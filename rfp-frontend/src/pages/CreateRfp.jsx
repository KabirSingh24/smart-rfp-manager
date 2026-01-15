import { useState } from "react";
import { post } from "../api";

export default function CreateRfp() {
    const [text, setText] = useState("");
    const [result, setResult] = useState(null);

    async function submit() {
        const res = await post("/rfps", { text });
        setResult(res);
    }

    return (
        <div className="card">
            <h2>Create RFP</h2>

            <textarea
                rows="6"
                value={text}
                onChange={e => setText(e.target.value)}
            />

            <button onClick={submit}>Create</button>

            {result && <StructuredView rfp={result} />}
        </div>
    );

}

function renderSpecs(specs) {
  if (!specs) return "N/A";
  if (typeof specs === "string") return specs;
  if (typeof specs === "object") {
    return Object.entries(specs)
      .map(([k, v]) => `${k}: ${v}`)
      .join(", ");
  }
  return String(specs);
}


function StructuredView({ rfp }) {
  const data = JSON.parse(rfp.structuredData);

  return (
    <div className="result">
      <h3>RFP Summary</h3>

      <div className="row">
        <span>Budget</span>
        <strong>${data.budget}</strong>
      </div>

      <div className="row">
        <span>Delivery</span>
        <strong>{data.delivery_days} days</strong>
      </div>

      <div className="row">
        <span>Payment</span>
        <strong>{data.payment_terms}</strong>
      </div>

      <div className="row">
        <span>Warranty</span>
        <strong>{data.warranty}</strong>
      </div>

      <h4>Items</h4>
      <ul>
        {data.items.map((item, i) => (
          <li key={i}>
            <strong>{item.name}</strong> — {item.quantity} × {renderSpecs(item.specs)}
          </li>
        ))}
      </ul>
    </div>
  );
}

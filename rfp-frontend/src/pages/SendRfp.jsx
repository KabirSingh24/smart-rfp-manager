import { useState } from "react";
import { post } from "../api";

export default function SendRfp() {
    const [text, setText] = useState("");
    const [vendorIds, setVendorIds] = useState("1");
    const [result, setResult] = useState(null);

    async function send() {
        const res = await post("/rfps/send", {
            text,
            vendorIds: vendorIds.split(",").map(Number)
        });
        setResult(res);
    }

    return (
        <div className="card">
            <h2>Send RFP</h2>

            <textarea rows="4" onChange={e => setText(e.target.value)} />
            <input
                placeholder="Vendor IDs (1,2)"
                onChange={e => setVendorIds(e.target.value)}
            />

            <button onClick={send}>Send</button>

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

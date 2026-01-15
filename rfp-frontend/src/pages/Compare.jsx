import { useState } from "react";
import { get } from "../api";

export default function Compare() {
  const [result, setResult] = useState(null);

  async function load() {
    const res = await get("/proposals/rfp/1/evaluate");
    setResult(res);
  }

  // find best vendor (highest score)
  const bestVendorId =
    result &&
    result.reduce((best, p) =>
      !best || p.score > best.score ? p : best,
      null
    )?.vendorId;

  return (
    <div className="card">
      <h2>Compare Proposals</h2>

      <button onClick={load}>Evaluate</button>

      {result && (
        <table className="table">
          <thead>
            <tr>
              <th>Vendor</th>
              <th>Price</th>
              <th>Delivery</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {result.map(p => {
              const jsonText =
                p.extractedProposal
                  ?.match(/\{[\s\S]*\}/)?.[0]; // safe JSON extract

              const data = jsonText ? JSON.parse(jsonText) : {};

              return (
                <tr
                  key={p.id}
                  className={p.vendorId === bestVendorId ? "winner" : ""}
                >
                  <td>
                    Vendor #{p.vendorId}
                    {p.vendorId === bestVendorId && " TOPSCORE "}
                  </td>
                  <td>₹{data.total_price}</td>
                  <td>{data.delivery_days} days</td>
                  <td>{p.score.toFixed(2)}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
}

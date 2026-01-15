import { useState } from "react";
import { post } from "../api";

export default function ParseProposal() {
  const [emailText, setEmailText] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  async function parse() {
    setLoading(true);
    const res = await post("/proposals/parse", {
      rfpId: 1,
      vendorId: 1,
      emailText
    });
    setResult(res);
    setLoading(false);
  }

  return (
    <div className="card">
      <h2>Parse Vendor Response</h2>

      <textarea
        rows="6"
        value={emailText}
        placeholder="Paste vendor email response here..."
        onChange={e => setEmailText(e.target.value)}
      />

      <button onClick={parse} disabled={loading || !emailText}>
        {loading ? "Parsing..." : "Parse Proposal"}
      </button>

      {result && (
        <div className="result">
          <h3>Parsed Proposal</h3>
          <pre>{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

import CreateRfp from "./pages/CreateRfp";
import SendRfp from "./pages/SendRfp";
import ParseProposal from "./pages/ParseProposal";
import Compare from "./pages/Compare";
import "./App.css";


export default function App() {
  return (
    <div className="page">
      <div className="container">
        <CreateRfp />
        <SendRfp />
        <ParseProposal />
        <Compare />
      </div>
    </div>
  );
}

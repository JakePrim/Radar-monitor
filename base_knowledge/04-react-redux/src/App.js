import logo from "./logo.svg";
import "./App.css";
import Counter from "./components/Counter";
import Person from "./components/Person";

function App() {
  return (
    <div className="App">
      <h3>Redux的工作流程</h3>
      <Counter />
      <Person />
    </div>
  );
}

export default App;

import StudentTitle from "./components/StudentTitle";
import AddStudent from "./components/AddStudent";
import StudentList from "./components/StudentList";

function App() {
  return (
    <div className="container">
      <StudentTitle />
      <AddStudent />
      <StudentList />
    </div>
  );
}

export default App;

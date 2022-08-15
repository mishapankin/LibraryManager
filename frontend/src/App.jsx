import Sidebar from "./components/Sidebar.jsx";
import Content from "./components/Content.jsx";
import "./style/App.scss"

const App = () => {
    return (
        <div id="App">
            <Sidebar/>
            <Content/>
        </div>
    );
};

export default App;

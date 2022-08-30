import Books from "./components/Books.jsx";
import {AppBar, Box, Toolbar} from "@mui/material";
import Sidebar from "./components/Sidebar.jsx";
import {Route, Routes} from "react-router-dom";
import Readers from "./components/Readers";

const App = () => {
    return (
        <Box display="flex">
            <Sidebar/>
            <Box component="main"
                sx={{ flexGrow: 1, bgcolor: 'background.default', p:3 }}>
                <Routes>
                    <Route path="/" element={<Books/>}/>
                    <Route path="/books" element={<Books/>}/>
                    <Route path="/readers" element={<Readers/>}/>
                </Routes>
            </Box>
        </Box>
    );
};

export default App;

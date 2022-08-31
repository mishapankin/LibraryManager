import Books from "./components/Books.jsx";
import {AppBar, Box, Toolbar} from "@mui/material";
import Sidebar from "./components/Sidebar.jsx";
import {Route, Routes} from "react-router-dom";
import Readers from "./components/Readers";
import BookInstances from "./components/BookInstances.jsx";
import Operations from "./components/Operations.jsx";

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
                    <Route path="/book_instances/:isbn" element={<BookInstances/>}/>
                    <Route path="/operations" element={<Operations/>}/>
                    <Route path="/operations/id=:idInit" element={<Operations/>}/>
                    <Route path="/operations/book_id=:bookInstanceIdInit" element={<Operations/>}/>
                </Routes>
            </Box>
        </Box>
    );
};

export default App;

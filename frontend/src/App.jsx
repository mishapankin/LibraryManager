import Books from "./components/Books.jsx";
import {Box} from "@mui/material";
import Sidebar from "./components/Sidebar.jsx";
import {Route, Routes} from "react-router-dom";

const App = () => {
    return (
        <Box sx={{display: "flex"}}>
            <Sidebar/>
            <Box component="main"
                sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3 }}>

                <Routes>
                    <Route path="/books" element={<Books/>}/>
                </Routes>
            </Box>
        </Box>
    );
};

export default App;

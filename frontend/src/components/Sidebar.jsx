import {Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText} from "@mui/material";
import {CalendarMonth, MenuBook, People} from "@mui/icons-material";
import {Link} from "react-router-dom";

const pages = [
    {name: "Книги", icon: <MenuBook/>, route: "/books"},
    {name: "Читатели", icon: <People/>, route: "/readers"},
    {name: "Операции", icon: <CalendarMonth/>, route: "/operataions"},
];

const Sidebar = () => {
    return (<Drawer
        sx={{
            width: 240,
            flexShrink: 0,
            '& .MuiDrawer-paper': {
                width: 240,
                boxSizing: 'border-box',
            },
        }}
        anchor="left"
        variant="permanent"
    >
        <List>
            {pages.map((val) =>
                <ListItem key={val.name} disablePadding>
                    <ListItemButton component={Link} to={val.route}>
                        <ListItemIcon>
                            {val.icon}
                        </ListItemIcon>
                        <ListItemText primary={val.name}/>
                    </ListItemButton>
                </ListItem>
            )}
        </List>
    </Drawer>);
};

export default Sidebar;
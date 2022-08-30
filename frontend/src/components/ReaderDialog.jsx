import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import {postRequest} from "../hooks.js";

const ReaderDialog = ( {newReader, setNewReader, isOpen, setIsOpen } ) => {
    const createNewUser = () =>
        postRequest("/api/post/reader", newReader).then(() => setIsOpen(false));

    return <Dialog open={isOpen}>
        <DialogTitle>Добавить нового читателя</DialogTitle>
        <DialogContent sx={{display: "flex", gap: 3, flexDirection: "column"}}>
            <TextField
                autoFocus
                id="new_reader_name"
                label="ФИО"
                margin="dense"
                value={newReader.name}
                onChange={e => setNewReader({ ...newReader, name: e.target.value})}
                required
            />
            <TextField
                autoFocus
                id="new_reader_address"
                label="Адрес"
                margin="dense"
                value={newReader.address}
                onChange={e => setNewReader({ ...newReader, address: e.target.value})}
                required
            />
        </DialogContent>
        <DialogActions sx={{display: "flex", justifyContent: "space-evenly", paddingBottom: 3}}>
            <Button onClick={() => setIsOpen(false)}>Отменить</Button>
            <Button onClick={createNewUser}
                    variant="contained">
                Добавить
            </Button>
        </DialogActions>
    </Dialog>;
};

export default ReaderDialog;
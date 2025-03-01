import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { userApi } from '../../services/user_service';
import { deviceApi } from '../../services/device_service';
import { logout } from '../login/auth-slice';

export default function SessionTimeoutPopup() {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const dispatch = useDispatch()
    const navigate = useNavigate();

    const logoutUser = () => {
        dispatch(logout());
        dispatch(userApi.util.resetApiState());
        dispatch(deviceApi.util.resetApiState());
        navigate('/login');
    }

    return (

        <Dialog
            open={true}
            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">
                {"Your session expired"}
            </DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Please re-login again
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => { logoutUser(); handleClose() }} autoFocus>
                    Logout
                </Button>
            </DialogActions>
        </Dialog>
    );
}
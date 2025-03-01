import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, Menu, MenuItem, MenuProps, Snackbar, alpha, styled, useMediaQuery, useTheme } from "@mui/material";
import MoreVertIcon from '@mui/icons-material/MoreVert';
import React, { useState } from "react";
import { User, useResetPasswordMutation } from "../../../services/user_service";
import EditIcon from '@mui/icons-material/Edit';
import LockResetIcon from '@mui/icons-material/LockReset';
import { UserActions } from "./UserActions";
import CloseIcon from '@mui/icons-material/Close';

type CustomMenuPropsUsers = {
    user: User;
}

const StyledMenu = styled((props: MenuProps) => (
    <Menu
        elevation={0}
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'right',
        }}
        transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
        }}
        {...props}
    />
))(({ theme }) => ({
    '& .MuiPaper-root': {
        borderRadius: 6,
        marginTop: theme.spacing(1),
        minWidth: 180,
        color:
            theme.palette.mode === 'light' ? 'rgb(55, 65, 81)' : theme.palette.grey[300],
        boxShadow:
            'rgb(255, 255, 255) 0px 0px 0px 0px, rgba(0, 0, 0, 0.05) 0px 0px 0px 1px, rgba(0, 0, 0, 0.1) 0px 10px 15px -3px, rgba(0, 0, 0, 0.05) 0px 4px 6px -2px',
        '& .MuiMenu-list': {
            padding: '4px 0',
        },
        '& .MuiMenuItem-root': {
            '& .MuiSvgIcon-root': {
                fontSize: 18,
                color: theme.palette.text.secondary,
                marginRight: theme.spacing(1.5),
            },
            '&:active': {
                backgroundColor: alpha(
                    theme.palette.primary.main,
                    theme.palette.action.selectedOpacity,
                ),
            },
        },
    },
}));

export default function CustomizedMenusUsers({ user }: CustomMenuPropsUsers) {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const [openCloseDetailView, setOpenCloseDetailView] = useState(false);
    const [openSuccess, setOpenSuccess] = React.useState(false);
    const [openFail, setOpenFail] = useState(false);
    const [openCloseLinkView, setOpenCloseLinkView] = useState(false);
    const [openSuccessCloseDetailView, setOpenSuccessCloseDetailView] = useState(false);

    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

    const [resetPassword] = useResetPasswordMutation({})

    const handleCloseSuccess = () => {
        setOpenSuccess(false);
    };

    const handleCloseFail = () => {
        setOpenFail(false);
    };

    return (
        <div>
            <IconButton
                aria-label="more"
                id="long-button"
                aria-controls={open ? 'long-menu' : undefined}
                aria-expanded={open ? 'true' : undefined}
                aria-haspopup="true"
                onClick={handleClick}
            >
                <MoreVertIcon />
            </IconButton>
            <StyledMenu
                id="demo-customized-menu"
                MenuListProps={{
                    'aria-labelledby': 'demo-customized-button',
                }}
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
            >
                <MenuItem onClick={() => {
                    setOpenCloseDetailView(true);
                    handleClose();
                }} disableRipple>
                    <EditIcon />
                    Update
                </MenuItem>
                <MenuItem onClick={() => {
                    setOpenCloseLinkView(true);
                    handleClose();
                }} disableRipple>
                    <LockResetIcon />
                    Reset Password
                </MenuItem>
            </StyledMenu>
            <UserActions open={openCloseDetailView} changeOpenStatus={setOpenCloseDetailView} user={user} />
            <Dialog
                maxWidth="lg"
                fullScreen={fullScreen}
                open={openCloseLinkView}
                onClose={() => setOpenCloseLinkView(false)}
                aria-labelledby="responsive-dialog-title"
            >
                <DialogTitle id="alert-dialog-title" sx={{ pr: 7 }}>
                    {"Are you sure to reset Password of user " + user.username + "?"}
                </DialogTitle>
                <DialogContent>
                    <IconButton
                        aria-label="close"
                        onClick={() => setOpenCloseLinkView(false)}
                        sx={{
                            position: 'absolute',
                            right: 15,
                            top: 8,
                            color: (theme) => theme.palette.grey[500],
                        }}
                    >
                        <CloseIcon />
                    </IconButton>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" color="warning" onClick={() => {
                        resetPassword({ username: user.username })
                            .unwrap()
                            .then((payload) => {
                                if (payload.status == 'S1000') {
                                    setOpenSuccess(true)
                                }
                            })
                            .catch((error) => {
                                setOpenFail(true)
                            });
                    }}>
                        RESET
                    </Button>
                    <Button variant="contained" onClick={() => setOpenCloseLinkView(false)} >
                        CANCEL
                    </Button>
                </DialogActions>
                <Snackbar open={openSuccess} autoHideDuration={6000} onClose={handleCloseSuccess} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                    <Alert onClose={handleCloseSuccess} severity="success" sx={{ width: '100%' }}>
                        {user.username} Password Reset success
                    </Alert>
                </Snackbar>

                <Snackbar open={openFail} autoHideDuration={6000} onClose={handleCloseFail} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                    <Alert onClose={handleCloseFail} severity="error" sx={{ width: '100%' }}>
                        {user.username} Password Reset failed
                    </Alert>
                </Snackbar>
            </Dialog>
        </div>
    );
}
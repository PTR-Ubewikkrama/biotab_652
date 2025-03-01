import { Alert, Box, Button, Dialog, DialogContent, IconButton, MenuItem, Select, Snackbar, TextField, Typography, useMediaQuery, useTheme } from "@mui/material";
import React, { useState } from "react";
import { useFormik } from "formik";
import { User, useUpdateUserMutation } from "../../../services/user_service";
import CloseIcon from '@mui/icons-material/Close';


type UserActionsProps = {
  user: User;
  open: boolean;
  changeOpenStatus: (status: boolean) => void;
}

export function UserActions({ user, open, changeOpenStatus }: UserActionsProps) {

  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openFail, setOpenFail] = useState(false);

  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

  const [edit, setEdit] = useState(false);
  const [updateUser] = useUpdateUserMutation()


  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseFail = () => {
    setOpenFail(false);
  };

  const formik = useFormik({
    initialValues: {
      username: user.username,
      group: user.group,
      status: user.status == "ACTIVE" ? "ACTIVE" : "TEMPORARY_BLOCKED"

    },
    onSubmit: (values, actions) => {
      if (!edit) {
        setEdit(true)
        actions.setSubmitting(false)
      } else {
        updateUser({
          username: values.username,
          status: values.status
        })
          .unwrap()
          .then((payload) => {
            if (payload.status == 'S1000') {
              actions.setSubmitting(false)
              setOpenSuccess(true)
              setEdit(false)
            }
          })
          .catch((error) => {
            actions.setSubmitting(false)
            setOpenFail(true)
          });
      }
    }
  });

  return (
    <Dialog
      fullScreen={fullScreen}
      open={open}
      onClose={() => changeOpenStatus(false)}
      aria-labelledby="responsive-dialog-title"
    >
      <DialogContent>
        <IconButton
          aria-label="close"
          onClick={() => changeOpenStatus(false)}
          sx={{
            position: 'absolute',
            right: 15,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
        <form onSubmit={formik.handleSubmit}>
          <Box sx={{ my: 3 }}>

            <Typography
              color="textSecondary"
              gutterBottom
              variant="body2"
            >
            </Typography>
          </Box>

          <TextField
            fullWidth
            label="Email"
            margin="normal"
            name="username"
            onBlur={formik.handleBlur}
            value={formik.values.username}
            variant="outlined"
            disabled
          />
          <TextField
            fullWidth
            label="Group"
            margin="normal"
            name="group"
            onBlur={formik.handleBlur}
            onChange={formik.handleChange}
            value={formik.values.group}
            variant="outlined"
            disabled
          />

          <Select
            fullWidth
            labelId="demo-simple-select-label"
            id="batchNo"
            value={formik.values.status}
            name="status"
            onChange={formik.handleChange}
            disabled={!edit}
          >
            <MenuItem value={"ACTIVE"}>ACTIVE</MenuItem>
            <MenuItem value={"TEMPORARY_BLOCKED"} color="orange">TEMPORARY_BLOCKED</MenuItem>
          </Select>
          <Box sx={{ py: 2 }}>
            <Button
              color="primary"
              disabled={formik.isSubmitting}
              fullWidth
              size="large"
              type="submit"
              variant="contained"
            >
              {edit ? "Save" : "Edit"}
            </Button>
          </Box>
        </form>
        <Snackbar open={openSuccess} autoHideDuration={6000} onClose={handleCloseSuccess} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
          <Alert onClose={handleCloseSuccess} severity="success" sx={{ width: '100%' }}>
            {user.username} Update success
          </Alert>
        </Snackbar>

        <Snackbar open={openFail} autoHideDuration={6000} onClose={handleCloseFail} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
          <Alert onClose={handleCloseFail} severity="error" sx={{ width: '100%' }}>
            {user.username} Update failed
          </Alert>
        </Snackbar>
      </DialogContent>

    </Dialog>
  );
};
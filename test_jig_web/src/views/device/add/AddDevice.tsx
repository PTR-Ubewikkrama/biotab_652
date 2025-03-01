import { Alert, Box, Button, Dialog, DialogContent, IconButton, Snackbar, TextField, Typography, useMediaQuery, useTheme } from "@mui/material";
import { useEffect, useState } from "react";
import { useFormik } from "formik";
import * as Yup from 'yup';
import { useAddDeviceMutation } from "../../../services/device_service";
import CloseIcon from '@mui/icons-material/Close';

type AddDeviceProps = {
  openModel: boolean
  close: () => void
}

export function AddDevice({ openModel, close }: AddDeviceProps) {

  const [openSuccess, setOpenSuccess] = useState(false);
  const [openFail, setOpenFail] = useState(false);
  const [addDevice] = useAddDeviceMutation()
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleClickOpenSuccess = () => {
    setOpenSuccess(true);
  };

  const handleCloseFail = () => {
    setOpenFail(false);
  };

  const handleClickOpenFail = () => {
    setOpenFail(true);
  };

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const formik = useFormik({
    initialValues: {
      deviceType : '',
      deviceName : '',
      deviceMac : ''


    },
    validationSchema: Yup.object({
      deviceType: Yup
        .string()
        .max(100)
        .required('Device Type is required'),
      deviceName: Yup
        .string()
        .max(50)
        .required('Device Name is required'),
        deviceMac: Yup
        .string()
        .max(100)
        .required('Mac Address is required'),
    }),
    onSubmit: (values, actions) => {
      addDevice({
        deviceType: values.deviceType,
        deviceName: values.deviceName,
        deviceMac: values.deviceMac,
      })
        .unwrap()
        .then((payload) => {
          if (payload.status == 'S1000') {
            setOpenSuccess(true)
            close()
          }
        })
        .catch((error) => {
          actions.setSubmitting(false)
          setOpenFail(true)
        });
    }
  });

  useEffect(() => {
    setOpenSuccess(false)
    setOpenFail(false)
  }, []);

  return (
    <Box>
      <Dialog
        fullScreen={fullScreen}
        open={openModel}
        onClose={close}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogContent>
          <IconButton
            aria-label="close"
            onClick={close}
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
              error={Boolean(formik.touched.deviceType && formik.errors.deviceType)}
              fullWidth
              helperText={formik.touched.deviceType && formik.errors.deviceType}
              label="Device Type"
              margin="normal"
              name="deviceType"
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              value={formik.values.deviceType}
              variant="outlined"
            />
            <TextField
              error={Boolean(formik.touched.deviceName && formik.errors.deviceName)}
              fullWidth
              helperText={formik.touched.deviceName && formik.errors.deviceName}
              label="Device Name"
              margin="normal"
              name="deviceName"
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              value={formik.values.deviceName}
              variant="outlined"
            />
            <TextField
              error={Boolean(formik.touched.deviceMac && formik.errors.deviceMac)}
              fullWidth
              helperText={formik.touched.deviceMac && formik.errors.deviceMac}
              label="Device Mac"
              margin="normal"
              name="deviceMac"
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              type="text"
              value={formik.values.deviceMac}
              variant="outlined"
            />
            <Box sx={{ py: 2, display:'flex', justifyContent:'center', alignItems:'center' }}>
              <Button
                color="primary"
                disabled={isSubmitting}
                size="large"
                type="submit"
                variant="contained"
                sx={{borderRadius:'30px', backgroundColor: "#24325f",}}
              >
                Add Device
              </Button>
            </Box>
          </form>
          <Snackbar open={openSuccess} autoHideDuration={2000} onClose={handleCloseSuccess} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={handleCloseSuccess} severity="success" sx={{ width: '100%' }}>
              {formik.values.deviceName} Save success
            </Alert>
          </Snackbar>
          <Snackbar open={openFail} autoHideDuration={2000} onClose={handleCloseFail} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={handleCloseFail} severity="error" sx={{ width: '100%' }}>
              {formik.values.deviceName} Save Failed
            </Alert>
          </Snackbar>
        </DialogContent>

      </Dialog>
    </Box>
  );
};
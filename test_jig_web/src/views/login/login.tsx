import { useFormik } from "formik";
import * as Yup from "yup";
import {
  Alert,
  Box,
  Button,
  Card,
  Container,
  Snackbar,
  TextField,
  Typography,
} from "@mui/material";
import PasswordReset from "./reset-password";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { loginSuccess } from "./auth-slice";
import wavetecLogo from "../../images/logoWave.png";
import { useLoginMutation } from "../../services/login_service";

export default function Login() {
  const [login] = useLoginMutation();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const dispatch = useDispatch();
  const [isReset, setReset] = useState(false);
  const [failMessage, setFailMessage] = useState("");

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: Yup.object({
      email: Yup.string()
        .email("Must be a valid email")
        .max(255)
        .required("Email is required"),
      password: Yup.string().max(100).required("Password is required"),
    }),
    onSubmit: (values, actions) => {
      login({
        email: values.email,
        password: values.password,
      })
        .unwrap()
        .then((payload) => {
          localStorage.removeItem("jwt");
          localStorage.setItem("jwt", payload.data.token);
          if (payload.data.status == "active") {
            setReset(false);
            dispatch(loginSuccess(payload));
            localStorage.setItem("user", values.email);
            navigate("/");
          } else if (payload.data.status == "INITIAL") {
            setReset(true);
            navigate("/password-reset");
          } else if (payload.status == "E1200") {
            actions.setSubmitting(false);
            actions.resetForm();
            setFailMessage("Un-authorized");
            setOpen(true);
          } else if (payload.status == "E1000") {
            actions.setSubmitting(false);
            actions.resetForm();
            setFailMessage("Login failed");
            setOpen(true);
          }
        })
        .catch((error) => {
          actions.setSubmitting(false);
          actions.resetForm();
          setFailMessage("Username or password is incorrect");
          setOpen(true);
        });
    },
  });

  return isReset ? (
    <PasswordReset />
  ) : (
    <Box
      component="main"
      sx={{
        alignItems: "center",
        display: "flex",
        justifyContent: "space-between",
        flexGrow: 1,
        minHeight: "100%",
        height: '300px',
        mx: 20,


      }}
      style={{ minHeight: "100vh" }}
    >
      <Box
        component="img"
        sx={{
          alignItems: "img",
          display: "flex",
          justifyContent: "right",
          minHeight: "10px",
          width: '400px',
          mx: 2,
        }}
        src={wavetecLogo}>
      </Box>
      <Card
        sx={{
          display: 'flex', flexDirection: 'row',
          height: '50%',
          maxWidth: 1600,
          borderRadius: '40px',
          position: 'relative',
          backdropFilter: "blur(3px)",
          backgroundColor: '#000000'
        }}
      >
        <Container maxWidth="sm"
          sx={{
            bgcolor: '#000000',
            opacity: '90%',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center'
          }}
        >
          <form onSubmit={formik.handleSubmit}>
            <Box sx={{ my: 3 }}>
              <Typography color="#FFFFFF" variant="h4" display="flex" justifyContent="center">
                Sign in
              </Typography>
            </Box>

            <TextField
              error={Boolean(formik.touched.email && formik.errors.email)}
              fullWidth
              helperText={formik.touched.email && formik.errors.email}
              label="Email Address"
              margin="normal"
              name="email"
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              type="email"
              value={formik.values.email}
              variant="filled"
              sx={{
                bgcolor: "#ffffff",
                borderRadius: "20px",
                color: 'red'
              }}
            />
            <TextField
              error={Boolean(formik.touched.password && formik.errors.password)}
              fullWidth
              helperText={formik.touched.password && formik.errors.password}
              label="Password"
              margin="normal"
              name="password"
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              type="password"
              value={formik.values.password}
              variant="filled"
              sx={{
                bgcolor: "#ffffff",
                borderRadius: "20px",
              }}
            />

            <Box sx={{ py: 2, display: 'flex', alignItems: 'center', justifyContent: 'center', }}>
              <Button
                // color="primary"
                disabled={formik.isSubmitting}
                size="large"
                type="submit"
                variant="contained"
                sx={{
                  backgroundColor: '#ed694f',
                  borderRadius: '30px',
                  '&:hover': {
                    backgroundColor: '#a8361e'
                  }
                }}

              >
                Sign In
              </Button>
            </Box>
          </form>
        </Container>
      </Card>
      <Snackbar
        open={open}
        autoHideDuration={4000}
        onClose={handleClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert onClose={handleClose} severity="error" sx={{ width: "100%" }}>
          {failMessage}
        </Alert>
      </Snackbar>
    </Box>
  );
}

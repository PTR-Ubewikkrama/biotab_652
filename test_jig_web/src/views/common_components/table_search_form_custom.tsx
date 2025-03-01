import { Box, Button, FormControl, Grid, InputLabel, MenuItem, Select, SelectChangeEvent, TextField, Typography } from '@mui/material'
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { useFormik } from 'formik';
import React, { useState } from 'react';
import * as Yup from 'yup';

type TableSearchCommonProps = {
    searchFun: (from: Date | null, filterType: string, filterValue: string) => void,
    dropDownItems?: { [key: string]: string };
}

export default function TableSearchFormCustom({ searchFun, dropDownItems = { "ALL": "ALL", "TEST_ID": "TEST ID", "DEVICE_MAC": "DEVICE MAC" } }: TableSearchCommonProps) {

    const [filterVal, setFilterType] = React.useState('');
    const [status, setFilterStatus] = React.useState('');
    const [from, setFromDate] = useState<Date | null>(null);

    const formik = useFormik({
        initialValues: {
            searchValue: '',
            password: ''
        },
        validationSchema: Yup.object({
            searchValue: Yup
                .string()
                .max(255),
            password: Yup
                .string()
                .max(100)
        }),
        onSubmit: (values, actions) => {
            searchFun(from, filterVal, values.searchValue);
        }
    });

    const handleFromDateChange = (date: Date | null) => {
        setFromDate(date);
    };

    const handleChange = (event: SelectChangeEvent) => {
        setFilterType(event.target.value as string);
        if (event.target.value === "ALL") {
            setFromDate(null);
            formik.setFieldValue("searchValue", "");
        }
    };

    const handleStatusChange = (event: SelectChangeEvent) => {
        setFilterStatus(event.target.value as string);
        if (event.target.value === "NONE") {
            setFromDate(null);
        }
    };

    return (
        <form onSubmit={formik.handleSubmit}>

            <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }}>
                <Grid item xs={4} sm={4} md={6} >
                    <TextField
                        sx={{ input: { color: 'grey' } }}
                        error={Boolean(formik.touched.searchValue && formik.errors.searchValue)}
                        fullWidth
                        helperText={formik.touched.searchValue && formik.errors.searchValue}
                        label="Search"
                        margin="normal"
                        name="searchValue"
                        onBlur={formik.handleBlur}
                        onChange={formik.handleChange}
                        type="string"
                        value={formik.values.searchValue}
                        variant="outlined"
                    />
                </Grid>
                <Grid item xs={4} sm={4} md={2} >
                    <FormControl style={{ minWidth: "100%" }} sx={{ mt: 2 }}>
                        <InputLabel id="demo-simple-select-label">Filter Type</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={filterVal}
                            label="Filter Type"
                            onChange={handleChange}
                            MenuProps={{
                                PaperProps: {
                                    sx: {
                                        "& .MuiMenuItem-root.Mui-selected": {
                                            backgroundColor: "#e0e0e0"
                                        },
                                        "& .MuiMenuItem-root:hover": {
                                            backgroundColor: "#e0e0e0"
                                        },
                                        "& .MuiMenuItem-root.Mui-selected:hover": {
                                            backgroundColor: "#e0e0e0"
                                        }
                                    }
                                }
                            }}

                        >
                            {Object.entries(dropDownItems).map(([key, value]) => (
                                <MenuItem value={key} key={key}>{value}</MenuItem>
                            ))}

                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={4} sm={4} md={2}>
                    <FormControl style={{ minWidth: "100%" }} sx={{ mt: 2 }}>
                        <LocalizationProvider dateAdapter={AdapterDayjs} >
                            <DatePicker
                                label="From"
                                value={from}
                                onChange={handleFromDateChange}
                            />
                        </LocalizationProvider>
                    </FormControl>
                </Grid>
                <Grid item xs={4} sm={4} md={2}  >
                    <Box sx={{ mt: 2, height: "500" }}>
                        <Button
                            color="primary"
                            fullWidth
                            size="large"
                            type="submit"
                            variant="contained"
                            sx={{ height: "54px" }}
                        >
                            Search
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </form>
    )
}
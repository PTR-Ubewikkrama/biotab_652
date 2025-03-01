import React, { useState } from "react";
import Select from "react-select";
import { useFormik } from "formik";
import { Height } from "@mui/icons-material";
import "../../../assets/css/custom_select.css";
import { makeStyles } from "@mui/material";

interface CustomSelectProps {
  onChange: any;
  options: any;
  value: any;
  className: any;
  name: any;
  id: any;
  onBlur: any;
  placeholder: any;
  submitted: boolean;
}

export default ({
  onChange,
  options,
  value,
  className,
  name,
  id,
  onBlur,
  placeholder,
  submitted,
}: CustomSelectProps) => {
  const defaultValue = (options: any[], values: any) => {
    return options ? options.find((option) => option.value === value) : "";
  };

  const customStyles = {
    control: (styles: any) => ({
      ...styles,
      paddingTop: 10,
      paddingBottom: 10,
      borderRadius: "8px",
      borderColor: "#9e9e9e",
    }),
  };
  return (
    <div className={className}>
      <Select
        options={options}
        name={name}
        id={id}
        isClearable
        placeholder={placeholder}
        value={submitted ? "" : defaultValue(options, value)}
        onChange={(value) => onChange(value)}
        styles={customStyles}
        // classNamePrefix="react-select"
      />
    </div>
  );
};

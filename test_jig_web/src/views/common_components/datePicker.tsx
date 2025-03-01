import React, { useState } from "react";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { Dayjs } from "dayjs";

interface DatePickerComponentProps {
  label: string;
  onChange: (date: Dayjs | null) => void;
}

export default function DatePickerComponent({
  label,
  onChange,
}: DatePickerComponentProps) {
  const [selectedDate, setSelectedDate] = useState<Dayjs | null>(null);

  const handleDateChange = (date: Dayjs | null) => {
    setSelectedDate(date);
    onChange(date);
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        label={label}
        value={selectedDate}
        onChange={handleDateChange}
        sx={{
          width: "150px",
          mr: 1
        }}
      />
    </LocalizationProvider>
  );
}

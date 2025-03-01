import TableRow from '@mui/material/TableRow';
import { styled } from '@mui/material';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';

export const StyledTableCell = styled(TableCell)(({ theme }) => ({
    padding: '8px',
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: '#9e9e9e',
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

export const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    '&:hover': {
        backgroundColor: theme.palette.grey[300], // Change this to your desired hover color
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));

export const StyledTableCellD = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: '#9e9e9e',
        color: theme.palette.common.white,
        border: '1px solid #ddd',  // Add border to the header cells
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
        border: '1px solid #ddd',  // Add border to the body cells
    },
}));

export const StyledTableRowD = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    '&:hover': {
        backgroundColor: theme.palette.grey[300], // Change this to your desired hover color
    },
    // Remove the border styling for last-child
    '&:last-child td, &:last-child th': {
        border: '1px solid #ddd',  // Ensure borders are present on the last row
    },
}));

export function checkPermission(permission: string): boolean {
    const token = localStorage.getItem("roles") as string
    if (permission != "" && token != null) {
        const roles = JSON.parse(token) as string[]
        return roles.includes(permission)
    }
    return false
}

export const customStyles = {
    control: (provided: any) => ({
        ...provided,
        minHeight: '35px',
        height: '38px',
    }),
    valueContainer: (provided: any) => ({
        ...provided,
        padding: '0px 8px',
    }),
    input: (provided: any) => ({
        ...provided,
        margin: 0,
        padding: 0,
    }),
    indicatorsContainer: (provided: any) => ({
        ...provided,
        height: '35px',
    }),
    menu: (provided: any) => ({
        ...provided,
        backgroundColor: 'white',
        opacity: '1 !important',
        boxShadow: 'none',
        border: '1px solid #ccc',
        zIndex: 9999,
        position: 'relative',
    }),
    menuPortal: (provided: any) => ({
        ...provided,
        zIndex: 9999,
    }),
};
import PropTypes from 'prop-types';
import { Box, MenuItem, MenuList, Popover, Typography } from '@mui/material';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logout } from '../login/auth-slice';

export const AccountPopover = (props: any) => {
  const { anchorEl, onClose, open, ...other } = props;
  const dispatch = useDispatch()
  const navigate = useNavigate();

  const logoutUser = () => {
    dispatch(logout());
    navigate('/login');
  }
  return (
    <Popover
      anchorEl={anchorEl}
      anchorOrigin={{
        horizontal: 'left',
        vertical: 'bottom'
      }}
      onClose={onClose}
      open={open}
      {...other}
    >
      <Box
        sx={{
          py: 1.5,
          px: 2
        }}
      >
        <Typography variant="overline">
          Account
        </Typography>
        <Typography
          color="text.secondary"
          variant="body2"
        >
          {localStorage.getItem("username") as string}
        </Typography>
      </Box>
      <MenuList
        disablePadding
        sx={{
          '& > *': {
            '&:first-of-type': {
              borderTopColor: 'divider',
              borderTopStyle: 'solid',
              borderTopWidth: '1px'
            },
            padding: '12px 16px'
          }
        }}
      >
        <MenuItem onClick={logoutUser}>
          Sign out
        </MenuItem>
      </MenuList>
    </Popover>
  );
};

AccountPopover.propTypes = {
  anchorEl: PropTypes.any,
  onClose: PropTypes.func,
  open: PropTypes.bool.isRequired
};

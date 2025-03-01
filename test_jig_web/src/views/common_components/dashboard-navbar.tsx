import { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { AppBar, Avatar, Badge, Box, IconButton, styled, Toolbar, Tooltip, Typography } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import { AccountPopover } from './account-popover';



const DashboardNavbarRoot = styled(AppBar)(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
  boxShadow: theme.shadows[3]
}));

interface DashboardNavbarProps {
  openSideBar: () => void
}

export const DashboardNavbar = ({ openSideBar }: DashboardNavbarProps) => {
  const settingsRef = useRef(null);
  const [openAccountPopover, setOpenAccountPopover] = useState(false);

  return (
    <>
      <DashboardNavbarRoot
        sx={{
          left: {
            lg: 250
          },
          width: {
            lg: 'calc(100% - 250px)'
          }
        }}
      >
        <Toolbar
          disableGutters
          sx={{
            minHeight: 64,
            left: 0,
            px: 2
          }}
        >
          <IconButton
            onClick={openSideBar}
            sx={{
              display: {
                xs: 'inline-flex',
                lg: 'none'
              }
            }}
          >
            <MenuIcon fontSize="small" />
          </IconButton>
          <Typography
            color="grey"
            variant="h5"
            sx={{ ml: 5 }}
          >
            Admin Panel
          </Typography>
          <Box sx={{ flexGrow: 1 }} />
          <Avatar
            onClick={() => setOpenAccountPopover(true)}
            ref={settingsRef}
            sx={{
              cursor: 'pointer',
              height: 40,
              width: 40,
              ml: 1,
              mr: 1
            }}
          >
            <ManageAccountsIcon fontSize="small" />
          </Avatar>
        </Toolbar>
      </DashboardNavbarRoot>
      <AccountPopover
        anchorEl={settingsRef.current}
        open={openAccountPopover}
        onClose={() => setOpenAccountPopover(false)}
      />
    </>
  );
};

DashboardNavbar.propTypes = {
  onSidebarOpen: PropTypes.func
};

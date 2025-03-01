import PropTypes from "prop-types";
import {
  Box,
  Divider,
  Drawer,
  ImageListItem,
  useMediaQuery,
} from "@mui/material";
import DashboardIcon from "@mui/icons-material/Dashboard";
import { NavItem } from "./nav-item";
import {
  Devices,
  People,
  Power,
  Air,
  Support,
} from "@mui/icons-material";
import exampleImage from '../../images/logoWave.png';
import BatterySaverIcon from '@mui/icons-material/BatterySaver';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import CompressIcon from '@mui/icons-material/Compress';
import MonitorHeartIcon from '@mui/icons-material/MonitorHeart';
import InventoryIcon from '@mui/icons-material/Inventory';
import ListAltIcon from '@mui/icons-material/ListAlt';

const items = [
  {
    href: "/",
    icon: <DashboardIcon fontSize="small" />,
    title: "Dashboard",
  },

  {
    href: "/users",
    icon: <People fontSize="small" />,
    title: "Users",
  },
  {
    href: "/devices",
    icon: <Devices fontSize="small" />,
    title: "Devices",
  },

  {
    href: "/air-dump-test",
    icon: <Air fontSize="small" />,
    title: "Air Pump Test",
  },
  {
    href: "/power-supply-test",
    icon: <Power fontSize="small" />,
    title: "Power Supply Test",
  },
  {
    href: "/valve-test",
    icon: <Support fontSize="small" />,
    title: "Valve Test",
  },
  {
    href: "/battery-test",
    icon: <BatterySaverIcon fontSize="small" />,
    title: "Battery Test",
  },
  {
    href: "/latch-button-test",
    icon: <RadioButtonCheckedIcon fontSize="small" />,
    title: "Latch Button Test",
  },
  {
    href: "/over-pressure-test",
    icon: <CompressIcon fontSize="small" />,
    title: "Over Pressure Test",
  },
  {
    href: "/pcb-test",
    icon: <MonitorHeartIcon fontSize="small" />,
    title: "PCB Test",
  },
  {
    href: "/hh-device",
    icon: <InventoryIcon fontSize="small" />,
    title: "HH Device",
  },
  ,
  {
    href: "/fas",
    icon: <ListAltIcon fontSize="small" />,
    title: "Final Assembly",
  },

];

type DashboardSidebarData = {
  open: boolean;
  closeSideBar: () => void;
};

export const DashboardSidebar = ({ open, closeSideBar }: DashboardSidebarData) => {
  const lgUp = useMediaQuery((theme: any) => theme.breakpoints.up('lg'), {
    defaultMatches: true,
    noSsr: false
  });

  const content = (
    <>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          height: '100%'
        }}
      >
        <div>
          <Box sx={{ pt: 4, pl: 5 }}>
            <ImageListItem>
              <img
                src={exampleImage}
                alt="description"
                style={{ width: 130, height: 65 }}
              />
            </ImageListItem>
          </Box>

        </div>
        <Divider
          sx={{
            borderColor: '#2D3748',
            my: 3
          }}
        />
        <Box sx={{ flexGrow: 1 }}>
          {items.map((item) => (
            <NavItem
              icon={item?.icon}
              href={item!.href}
              title={item!.title}
            />
          ))}
        </Box>

        <Divider sx={{ borderColor: '#2D3748' }} />
      </Box>
    </>
  );

  if (lgUp) {
    return (
      <Drawer
        anchor="left"
        open
        PaperProps={{
          sx: {
            width: 250,
            borderRightStyle: 'dashed',
            borderColor: "#9d9e9d"
          },
        }}
        variant="permanent"
      >
        {content}
      </Drawer>
    );
  }

  return (
    <Drawer
      anchor="left"
      onClose={closeSideBar}
      open={open}
      PaperProps={{
        sx: {
          width: 250,
          borderRightStyle: 'dashed',
          borderColor: "#9d9e9d"
        },
      }}
      sx={{ zIndex: (theme) => theme.zIndex.appBar + 100 }}
      variant="temporary"
    >
      {content}
    </Drawer>
  );
};

DashboardSidebar.propTypes = {
  onClose: PropTypes.func,
  open: PropTypes.bool
};

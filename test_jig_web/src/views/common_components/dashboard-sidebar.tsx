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
  Power,
  Air,
  Support,
} from "@mui/icons-material";
import exampleImage from '../../images/logoWave.png';
import BatterySaverIcon from '@mui/icons-material/BatterySaver';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import CompressIcon from '@mui/icons-material/Compress';
import MonitorHeartIcon from '@mui/icons-material/MonitorHeart';
import ListAltIcon from '@mui/icons-material/ListAlt';
import SettingsInputCompositeIcon from '@mui/icons-material/SettingsInputComposite';
import CableIcon from '@mui/icons-material/Cable';
import AcUnitIcon from '@mui/icons-material/AcUnit';
import DeveloperBoardIcon from '@mui/icons-material/DeveloperBoard';
import TvIcon from '@mui/icons-material/Tv';

const items = [
  {
    href: "/",
    icon: <DashboardIcon fontSize="small" />,
    title: "Dashboard",
  },
  {
    href: "/air-pump",
    icon: <Air fontSize="small" />,
    title: "AirPump",
  },
  {
    href: "/air-pump-2",
    icon: <Air fontSize="small" />,
    title: "AirPump v2.0",
  },
  {
    href: "/power-supply-test",
    icon: <Power fontSize="small" />,
    title: "Power Supply Test",
  },
  {
    href: "/power-supply-test-2",
    icon: <Power fontSize="small" />,
    title: "Power Supply Test v2.0",
  },
  {
    href: "/valve-test",
    icon: <Support fontSize="small" />,
    title: "Valve Test",
  },
  {
    href: "/power-pcb-test",
    icon: <MonitorHeartIcon fontSize="small" />,
    title: "Power PCB Test",
  },
  {
    href: "/power-pcb-test-2",
    icon: <MonitorHeartIcon fontSize="small" />,
    title: "Power PCB Test v2.0",
  },
  {
    href: "/op-valve-test",
    icon: <RadioButtonCheckedIcon fontSize="small" />,
    title: "OP Valve Test",
  },
  {
    href: "/display-test",
    icon: <TvIcon fontSize="small" />,
    title: "Display Test",
  },
  {
    href: "/valve-sequence-test",
    icon: <CompressIcon fontSize="small" />,
    title: "Valve Sequence Test",
  },
  {
    href: "/valve-card-test",
    icon: <CompressIcon fontSize="small" />,
    title: "Valve Card Test",
  },
  {
    href: "/mani-fold-leak-test",
    icon: <BatterySaverIcon fontSize="small" />,
    title: "Manifold Leak Test",
  },
  {
    href: "/ui-pcb-test",
    icon: <SettingsInputCompositeIcon fontSize="small" />,
    title: "UI PCB Test",
  },
  {
    href: "/cable-test",
    icon: <CableIcon fontSize="small" />,
    title: "Cable Test",
  },
  {
    href: "/fan-test",
    icon: <AcUnitIcon fontSize="small" />,
    title: "Fan Test",
  },
  {
    href: "/bt-device",
    icon: <DeveloperBoardIcon fontSize="small" />,
    title: "BT Device",
  },
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

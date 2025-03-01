import PropTypes from 'prop-types';
import { Box, Button, Card, Collapse, Grid, List, ListItem } from '@mui/material';
import { Link, useLocation } from 'react-router-dom';
import { useState } from 'react';

export type NavItemProps = {
  href: string,
  icon: React.ReactNode,
  title: string,
  // image: any
  // childs: NavItemProps[]
}

export const NavItem = ({ href, icon, title }: NavItemProps) => {

  const location = useLocation();
  const active = href ? (location.pathname === href) : false;
  const [open, setOpen] = useState(false);


  return (
    <>
      <ListItem
        disableGutters
        sx={{
          display: 'flex',
          mb: 0.5,
          py: 0,
          px: 2,
          paddingLeft: 0,
        }}
      >
        <Link
          to={href}
          onClick={() => setOpen(!open)}
          style={{ textDecoration: 'none', width: '100%' }}
        >
          <Grid
            container
          >
            <Grid sx={{
              width: 3,
              bgcolor: active ? '#c4fbfd' : '',
            }}>
            </Grid>
            <Grid
              sx={{
                width: '95%',
              }}>

              <Button
                startIcon={icon}
                disableRipple
                sx={{
                  background: active ? 'linear-gradient(to right, #24325f, #384d93)' : '',
                  borderRadius: 0,
                  color: active ? 'white' : 'grey',
                  fontWeight: active ? 'fontWeightBold' : '',
                  justifyContent: 'flex-start',
                  textAlign: 'left',
                  textTransform: 'none',
                  width: '100%',
                  '& .MuiButton-startIcon': {
                    color: active ? 'white' : '#9CA3AF'
                  },
                  '&:hover': {
                    backgroundColor: active ? '#00ADEE' : '#00ADEE'
                  }
                }}
              // endIcon={childs.length != 0 ? (open ? <ExpandLess /> : <ExpandMore />) : ""}
              >
                {/* <Box
              sx={{
                backgroundImage: `url(${image})`,
                backgroundRepeat: 'no-repeat',
                backgroundSize: 'cover',
                width:'30px',
                height: '30px',
                mr: 2
              }}
            ></Box> */}
                <Box sx={{ flexGrow: 1 }} >
                  {title}
                </Box>

              </Button>
            </Grid>
          </Grid>
        </Link>

      </ListItem>
      {/* <Box sx={{ flexGrow: 1, ml: 2 }}>
              <NavItem
                key={title}
                icon={icon}
                href={href}
                title={title}
              />
        </Box> */}
      <Collapse in={open} timeout="auto" unmountOnExit>
        <List component="div" disablePadding>
          {/* <Box sx={{ flexGrow: 1, ml: 2 }}>
            {childs.map((item) => (
              <NavItem
                key={title}
                icon={icon}
                href={href}
                title={title}
              />
             ))}
          </Box> */}
        </List>
      </Collapse>
    </>
  );
};

NavItem.propTypes = {
  href: PropTypes.string,
  icon: PropTypes.node,
  title: PropTypes.string
};

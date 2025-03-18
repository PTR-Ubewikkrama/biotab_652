import { Avatar, Box, Card, CardContent, Grid, Typography } from '@mui/material';
import { ReactNode } from 'react';
import { Link, useNavigate } from 'react-router-dom';

type SummaryCardData = {
  title: string,
  icon: ReactNode,
  value: string,
  path: string,
  color: string,
  image: any
}

export function SummaryCard({ title, value, path, color, image }: SummaryCardData) {
  const navigate = useNavigate();

  return (
    <Link to={path} style={{ textDecoration: 'none' }}>
      <Card
        sx={{
          maxWidth: 1600, backgroundColor: "#3b487c",
          boxShadow: "1px 1px 5px 2px #e8e8e8",
          transition: 'transform 0.3s',
          backgroundImage: `url(${image})`,
          backgroundRepeat: 'no-repeat',
          backgroundSize: 'cover',
          borderRadius: '20px',
          '&:hover': {
            transform: 'scale(1.1)', // Adjust the scaling factor as desired
            background: 'linear-gradient(to bottom, #c4fbfd, #c4fbfd)',
            bgcolor: '#07032B',
            borderRadius: '20px',
          },
        }}
      // onClick={() => navigate(path)}
      >
        <CardContent>
          <Grid
            container
            sx={{ justifyContent: 'space-between' }}
          >
            <Grid item>
              <Typography
                sx={{ mt: 1 }}
                color="black"
                gutterBottom
                variant="h6"
              >
                {title}
              </Typography>
              <Typography
                sx={{ mt: 2, color: "grey", fontSize: 11 }}
                color="textPrimary"
              >
                Total count
              </Typography>
              <Typography
                sx={{ mt: 0.5, fontWeight: "bold", color: "black" }}
                color="textPrimary"
                variant="h4"
              >
                {value}
              </Typography>
            </Grid>
            <Grid item>
              {/* <Avatar
                sx={{
                  backgroundColor: color,
                  height: 35,
                  width: 35
                }}
              >
                {icon}
              </Avatar> */}
            </Grid>
          </Grid>

        </CardContent>
      </Card>
    </Link>
  );
}
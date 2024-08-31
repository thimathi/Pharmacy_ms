import React from 'react';
import { Box, Typography } from '@mui/material';
import AccountDetails from './AccountDetails'; // Adjust the path as necessary

export default function AddUserDetails() {
    return (
        <Box sx={{ p: 3 }}>
            <Typography variant="h4" sx={{ mb: 3 }}>
                Add New User
            </Typography>
            <AccountDetails />
        </Box>
    );
}

import React from 'react';
import { Box, Typography } from '@mui/material';
import CustomerView from './CustomerView'; // Adjust the path as necessary

export default function CustomerViewPage() {
    return (
        <Box sx={{ p: 3 }}>
            <Typography variant="h4" sx={{ mb: 3 }}>
                Add New User
            </Typography>
            <CustomerView />
        </Box>
    );
}

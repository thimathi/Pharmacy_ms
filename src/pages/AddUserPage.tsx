import React from 'react';
import { Box, Typography } from '@mui/material';
import AddUserForm from './AddUserForm'; // Adjust the path as necessary

export default function AddUserPage() {
    return (
        <Box sx={{ p: 3 }}>
            <Typography variant="h4" sx={{ mb: 3 }}>
                Add New User
            </Typography>
            <AddUserForm />
        </Box>
    );
}

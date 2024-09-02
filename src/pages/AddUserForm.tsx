import axios from 'axios';
import React, { useState } from 'react';

import { Box, Button, MenuItem, TextField, Typography } from '@mui/material';

const userTypes = [
    { value: '1', label: 'Admin' },
    { value: '2', label: 'Cashier' },
    { value: '3', label: 'InventoryManager' },
    { value: '4', label: 'SalasPerson' },
    { value: '5', label: 'InactiveUser' },
];

export default function AddUserForm() {
    const [user, setUser] = useState({
        name: '',
        address: '',
        mobile: '',
        email: '',
        username: '',
        password: '',
        userType: ''
    });

    // @ts-ignore
    const handleChange = (prop) => (event) => {
        setUser({ ...user, [prop]: event.target.value });
    };

    // @ts-ignore
    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const token = sessionStorage.getItem('token');
            const response = await axios.put('http://localhost:5070/createUser', {
                name: user.name,
                address: user.address,
                mobile: user.mobile,
                email: user.email,
                userTypeId: user.userType
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            alert(response.data)
            console.log('User added successfully:', response.data);
            // Add logic here for success handling, e.g., displaying a success message

            // Clear form fields after successful submission
            setUser({
                name: '',
                address: '',
                mobile: '',
                email: '',
                username: '',
                password: '',
                userType: ''
            });

        } catch (error) {
            console.error('Failed to add user:', error);
            // Add logic here for error handling, e.g., displaying an error message
        }
    };

    return (
        <Box
            component="form"
            sx={{
                display: 'flex',
                flexDirection: 'column',  // Ensures the form fields are stacked vertically
                m: 2,
                '& .MuiTextField-root': { m: 1, width: '100%' }  // Adjust width as necessary
            }}
            noValidate
            autoComplete="off"
            onSubmit={handleSubmit}
        >
            <Typography variant="h6" sx={{ mb: 2 }}>
                Add New User
            </Typography>
            <TextField
                required
                label="Name"
                value={user.name}
                onChange={handleChange('name')}
            />
            <TextField
                required
                label="Address"
                value={user.address}
                onChange={handleChange('address')}
            />
            <TextField
                required
                label="Mobile"
                value={user.mobile}
                onChange={handleChange('mobile')}
                inputProps={{
                    maxLength: 10
                }}
            />
            <TextField
                required
                label="Email"
                value={user.email}
                onChange={handleChange('email')}
            />
            <TextField
                select
                label="User Type"
                value={user.userType}
                onChange={handleChange('userType')}
                helperText="Please select the user type"
            >
                {userTypes.map((option) => (
                    <MenuItem key={option.value} value={option.value}>
                        {option.label}
                    </MenuItem>
                ))}
            </TextField>
            <Button type="submit" variant="contained" color="primary" sx={{ mt: 3, alignSelf: 'flex-start' }}>
                Add User
            </Button>
        </Box>
    );
}

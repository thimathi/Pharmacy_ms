import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Box, Button, TextField, Typography, Select, MenuItem, InputLabel, FormControl } from '@mui/material';
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import { Iconify } from "../components/iconify";

export default function AccountDetails() {
    const [user, setUser] = useState({
        name: '',
        address: '',
        mobile: '',
        email: '',
        username: '',
        password: '',
        usertype: '', // Initialize usertype state
    });
    const [showPassword, setShowPassword] = useState(false);

    useEffect(() => {
        const fetchUserData = async () => {
            const token = sessionStorage.getItem('token');
            const userId = sessionStorage.getItem('userId');

            try {
                const response = await axios.post('http://localhost:5070/getUserById', {
                    userId
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.data) {
                    setUser({
                        ...response.data,
                    });
                }
            } catch (error) {
                console.error('Failed to fetch user data:', error);
            }
        };

        fetchUserData();
    }, []);

    // @ts-ignore
    const handleChange = (prop) => (event) => {
        setUser(prev => ({ ...prev, [prop]: event.target.value }));
    };

    const handleUpdate = async () => {
        const token = sessionStorage.getItem('token');
        const userId = sessionStorage.getItem('userId'); // Retrieve userId from session storage
        const { name, email, usertype, ...rest } = user;

        function getUserTypeId(roleName:any) {
            switch (roleName) {
                case "Admin":
                    return 1;
                case "Cashier":
                    return 2;
                case "Inventory Manager":
                    return 3;
                case "Sales Person":
                    return 4;
                case "Inactive User":
                    return 5;
                default:
                    return null; // or throw an error if the role is unrecognized
            }
        }

        try {
            const response = await axios.patch('http://localhost:5070/updateUser', {
                id:userId, // Include the userId in the request payload
                userTypeId:getUserTypeId(usertype), // Include the usertype in the request payload
                ...rest,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 200) {
                alert("Update successfully!");
                window.location.reload();  // Optionally, reload to fetch updated data or refresh state
            }
        } catch (error) {
            console.error('Failed to update user:', error);
            alert('Failed to update user data.');
        }
    };

    return (
        <Box
            component="form"
            sx={{
                display: 'flex',
                flexDirection: 'column',
                m: 2,
                '& .MuiTextField-root': { m: 1, width: '100%' }
            }}
            noValidate
            autoComplete="off"
            onSubmit={event => {
                event.preventDefault();
                handleUpdate();
            }}
        >
            <Typography variant="h6" sx={{ mb: 2 }}>
                My Account
            </Typography>
            <TextField label="Name" value={user.name} disabled />
            <TextField label="Email" value={user.email} disabled />
            <TextField label="Address" value={user.address} onChange={handleChange('address')} />
            <TextField label="Mobile" value={user.mobile} onChange={handleChange('mobile')} inputProps={{
                maxLength: 10
            }}/>
            <FormControl fullWidth sx={{ m: 1 }}>
                <InputLabel id="usertype-label">User Type</InputLabel>
                <Select
                    labelId="usertype-label"
                    value={user.usertype}
                    onChange={handleChange('usertype')}
                    displayEmpty
                    fullWidth
                    disabled  // This field is disabled and cannot be changed
                >
                    <MenuItem value=""><em>None</em></MenuItem>
                    <MenuItem value="Admin">Admin</MenuItem>
                    <MenuItem value="Cashier">Cashier</MenuItem>
                    <MenuItem value="Inventory Manager">Inventory Manager</MenuItem>
                    <MenuItem value="Sales Person">Sales Person</MenuItem>
                    <MenuItem value="Inactive User">Inactive User</MenuItem>
                </Select>
            </FormControl>
            <TextField label="Username" value={user.username} onChange={handleChange('username')} />
            <TextField
                fullWidth
                name="password"
                label="Password"
                value={user.password}
                onChange={(e) => setUser({...user, password: e.target.value})}
                type={showPassword ? 'text' : 'password'}
                InputProps={{
                    endAdornment: (
                        <InputAdornment position="end">
                            <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                                <Iconify icon={showPassword ? 'solar:eye-bold' : 'solar:eye-closed-bold'} />
                            </IconButton>
                        </InputAdornment>
                    ),
                }}
                sx={{ mb: 3 }}
            />
            <Button type="submit" variant="contained" color="primary" sx={{ mt: 3, alignSelf: 'center' }}>
                Update
            </Button>
        </Box>
    );
}

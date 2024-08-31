import axios from 'axios';
import React, { useState, useCallback } from 'react';

import { Select, Dialog, Button, Popover, TableRow, Checkbox, MenuItem, TableCell, TextField, IconButton, DialogTitle, DialogContent, DialogActions } from '@mui/material';

import { Iconify } from 'src/components/iconify';

export function UserTableRow({ row, selected, onSelectRow, onSave }: any) {
    const [editMode, setEditMode] = useState(false);
    const [editableRow, setEditableRow] = useState({ ...row });
    const [openPopover, setOpenPopover] = useState<HTMLButtonElement | null>(null);
    const [openDialog, setOpenDialog] = useState(false);

    const handleOpenPopover = useCallback((event: React.MouseEvent<HTMLButtonElement>) => {
        setOpenPopover(event.currentTarget);
    }, []);

    const handleClosePopover = useCallback(() => {
        setOpenPopover(null);
    }, []);

    const handleToggleEdit = () => {
        setEditMode(!editMode);
        setEditableRow({ ...row });
    };

    const handleOpenDialog = () => {
        setOpenDialog(true);
        setEditableRow({ ...row });
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
    };

    const handleChange = (prop: string, value: any) => {
        // @ts-ignore
        setEditableRow(prev => ({ ...prev, [prop]: value }));
    };

    const handleSave = async () => {
        try {
            const token = sessionStorage.getItem('token');
            const { name, email, usertype, ...rest } = editableRow;
            const filteredData = {
                ...rest,
                userTypeId: usertype
            };

            const response = await axios.patch('http://localhost:5070/updateUser', filteredData, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 200) {
                onSave(editableRow); // Pass the updated data back up for re-render
                handleCloseDialog(); // Close dialog after successful update
                alert("Update successfully!");
                window.location.reload();
            }
        } catch (error) {
            console.error('Failed to update user:', error);
        }
    };
    const handleDelete = async () => {
        try {
            const token = sessionStorage.getItem('token');
            const response = await axios.delete('http://localhost:5070/deleteUser', {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                data: {
                    userId: row.id // Assuming `row.id` is the ID of the user to be deleted
                }
            });

            if (response.status === 200) {
                alert("User deleted successfully!");
                window.location.reload(); // Reload to update the list of users
            }
        } catch (error) {
            console.error('Failed to delete user:', error);
            alert("Failed to delete user.");
        }
    };

    return (
        <TableRow hover tabIndex={-1} role="checkbox" selected={selected}>
            <TableCell padding="checkbox">
                <Checkbox disableRipple checked={selected} onChange={onSelectRow} />
            </TableCell>
            <TableCell component="th" scope="row">{row.id}</TableCell>
            <TableCell>{row.name}</TableCell>
            <TableCell>{row.address}</TableCell>
            <TableCell>{row.mobile}</TableCell>
            <TableCell>{row.email}</TableCell>
            <TableCell>{row.username}</TableCell>
            <TableCell>{row.password}</TableCell>
            <TableCell>{row.usertype}</TableCell>
            <TableCell align="right">
                <IconButton onClick={handleOpenPopover}>
                    <Iconify icon="eva:more-vertical-fill" />
                </IconButton>
                <Popover
                    open={!!openPopover}
                    anchorEl={openPopover}
                    onClose={handleClosePopover}
                    anchorOrigin={{ vertical: 'top', horizontal: 'left' }}
                    transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                >
                    <MenuItem onClick={handleOpenDialog}>
                        <Iconify icon="solar:pen-bold" />
                        Edit
                    </MenuItem>
                    <MenuItem onClick={handleDelete} sx={{ color: 'error.main' }}>
                        <Iconify icon="solar:trash-bin-trash-bold" />
                        Delete
                    </MenuItem>
                </Popover>
            </TableCell>

            <Dialog open={openDialog} onClose={handleCloseDialog} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Update User</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="address"
                        label="Address"
                        type="text"
                        fullWidth
                        value={editableRow.address}
                        onChange={(e) => handleChange('address', e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        id="mobile"
                        label="Mobile"
                        type="text"
                        fullWidth
                        value={editableRow.mobile}
                        onChange={(e) => handleChange('mobile', e.target.value)}
                        inputProps={{
                            maxLength: 10
                        }}
                    />
                    <Select
                        value={editableRow.usertype}
                        onChange={(e) => handleChange('usertype', e.target.value)}
                        displayEmpty
                        fullWidth
                    >
                        <MenuItem value=""><em>None</em></MenuItem>
                        <MenuItem value={1}>Admin</MenuItem>
                        <MenuItem value={2}>Cashier</MenuItem>
                        <MenuItem value={3}>Inventory Manager</MenuItem>
                        <MenuItem value={4}>Sales Person</MenuItem>
                        <MenuItem value={5}>Inactive User</MenuItem>
                    </Select>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleSave} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </TableRow>
    );
}

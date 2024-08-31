import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import { Box, Button, Drawer, TextField, Typography } from '@mui/material';

import { _users } from 'src/_mock';
import { DashboardContent } from 'src/layouts/dashboard';

import { Iconify } from 'src/components/iconify';
import { Scrollbar } from 'src/components/scrollbar';

import {useTable} from "../sections/user/view/index.js";
import { TableNoData } from '../sections/user/table-no-data';
import { UserTableRow } from '../sections/user/user-table-row';
import { UserTableHead } from '../sections/user/user-table-head';
import { TableEmptyRows } from '../sections/user/table-empty-rows';
import { emptyRows, applyFilter, getComparator } from '../sections/user/utils';


export default function CustomerView() {
    const navigate = useNavigate();
    const [filterName, setFilterName] = useState('');
    const [selectedCustomer, setSelectedCustomer] = useState(null);
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    const table = useTable();

    const dataFiltered = applyFilter({
        inputData: _users,
        comparator: getComparator(table.order, table.orderBy),
        filterName,
    });

    const notFound = !dataFiltered.length && !!filterName;

    // @ts-ignore
    const handleSelectCustomer = (customer) => {
        setSelectedCustomer(customer);
        setIsDrawerOpen(true);
    };

    const handleCloseDrawer = () => {
        setIsDrawerOpen(false);
        setSelectedCustomer(null);
    };

    // @ts-ignore
    return (
        <DashboardContent>
            <Box display="flex" alignItems="center" mb={5}>
                <Typography variant="h4" flexGrow={1}>
                    Customers
                </Typography>
                <Button
                    variant="contained"
                    color="inherit"
                    startIcon={<Iconify icon="mingcute:add-line" />}
                    onClick={() => navigate('/customer/add')}
                >
                    New customer
                </Button>
            </Box>
            <Box sx={{ display: 'flex', height: 'calc(100% - 48px)' }}>
                <Box sx={{ flexGrow: 1 }}>
                    <Scrollbar>
                        <Table sx={{ minWidth: 800 }}>
                            <UserTableHead
                                order={table.order}
                                orderBy={table.orderBy}
                                rowCount={_users.length}
                                numSelected={table.selected.length}
                                onSort={table.onSort}
                                onSelectAllRows={(checked) =>
                                    table.onSelectAllRows(
                                        checked,
                                        _users.map((user) => user.id)
                                    )
                                }
                                headLabel={[
                                    { id: 'name', label: 'Name' },
                                    { id: 'company', label: 'Company' },
                                    { id: 'role', label: 'Role' },
                                    { id: 'isVerified', label: 'Verified', align: 'center' },
                                    { id: 'status', label: 'Status' },
                                    { id: '' },
                                ]}
                            />
                            <TableBody>
                                {dataFiltered
                                    .map((customer) => (
                                        <UserTableRow
                                            key={customer.id}
                                            row={customer}
                                            selected={table.selected.includes(customer.id)}
                                            onSelectRow={() => handleSelectCustomer(customer)}
                                        />
                                    ))}
                                <TableEmptyRows
                                    height={68}
                                    emptyRows={emptyRows(table.page, table.rowsPerPage, _users.length)}
                                />
                                {notFound && <TableNoData searchQuery={filterName} />}
                            </TableBody>
                        </Table>
                    </Scrollbar>
                </Box>
                <Drawer
                    anchor="right"
                    open={isDrawerOpen}
                    onClose={handleCloseDrawer}
                    sx={{ width: 400, flexShrink: 0 }}
                    variant="persistent"
                >
                    {selectedCustomer && (
                        <Box sx={{ p: 2, width: 400 }}>
                            <Typography variant="h6">Edit Customer</Typography>
                            <TextField
                                fullWidth
                                label="Name"
                                // @ts-ignore
                                value={selectedCustomer.name}
                                onChange={(e) => handleEditChange('name', e.target.value)}
                                margin="normal"
                            />
                            {/* Additional fields can be added here similarly */}
                        </Box>
                    )}
                </Drawer>
            </Box>
        </DashboardContent>
    );
}

// Function to handle edits to customer details
// @ts-ignore
function handleEditChange(field, value) {
    // This function would update the customer data, possibly using a state management solution or direct mutation
}

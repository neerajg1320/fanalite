import React, { useState, useEffect } from 'react';

import firebase from 'firebase';
import realtimeDatabase from '../../firebaseConfig'

import { AddCircleOutlineRounded, DeleteOutlineRounded, Edit } from '@material-ui/icons';
import { Button, TextField, Container, IconButton, List, ListItem, ListItemSecondaryAction, ListItemText, Dialog, DialogContent, DialogActions } from '@material-ui/core';

import {format} from 'date-fns';


function Regex() {

    const [todos, setTodos] = useState([]);
    const [input, setInput] = useState('');
    const [open, setOpen] = useState(false);
    const [update, setUpdate] = useState('');
    const [toUpdateId, setToUpdateId] = useState('');
  
  
    useEffect(() => {
      console.log('useEffect Hook!!!');
  
      // .once can be used for onetime only
      realtimeDatabase.ref('todos').on('value', (snapshot) => {
        const todos = snapshot.val();
        const todoList = [];
        for (let id in todos) {
          const name = todos[id].name;
          const datetime = todos[id].datetime;
          
          todoList.push({
            id, 
            name, 
            datetime: format(new Date(datetime), 'yyyy/MM/dd HH:mm:ss SSS')
          });
        }
        
        console.log('todoList:', todoList);
        
        setTodos(todoList);
      })
  
    }, []);
  
    // On addition we get two value event with different timestamps
    const addTodo = (event) => {
      event.preventDefault();
  
      realtimeDatabase.ref('todos').push({
        name: input,
        datetime: firebase.database.ServerValue.TIMESTAMP
      })
  
      // Check if we can declare successful addition.
      setInput('');
    }
  
    const deleteTodo = (id) => {
      realtimeDatabase.ref('todos').child(id).remove();
    }
  
    const openUpdateDialog = (todo) => {
      setOpen(true);
      setToUpdateId(todo.id);
      setUpdate(todo.name);
    }
  
    const editTodo = () => {
      realtimeDatabase.ref('todos').child(toUpdateId).set({
        name: update,
        datetime: firebase.database.ServerValue.TIMESTAMP
      })
      setOpen(false);
    }
  
    const handleClose = () => {
      setOpen(false);
    };
  
    return (
      <Container maxWidth="sm">
  
        <form noValidate>
  
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="todo"
            label="Enter ToDo"
            name="todo"
            autoFocus
            value={input}
            onChange={event => setInput(event.target.value)}
          />
  
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            onClick={addTodo}
            disabled={!input}
            startIcon={<AddCircleOutlineRounded />}
          >
            Add Todo
        </Button>
  
        </form>
  
        <List dense={true}>
          {
            todos.map(todo => (
  
              <ListItem key={todo.id} >
  
                <ListItemText
                  primary={todo.name}
                  secondary={todo.datetime}
                />
  
                <ListItemSecondaryAction>
                  <IconButton edge="end" aria-label="Edit" onClick={() => openUpdateDialog(todo)}>
                    <Edit />
                  </IconButton>
                  <IconButton edge="end" aria-label="delete" onClick={() => deleteTodo(todo.id)}>
                    <DeleteOutlineRounded />
                  </IconButton>
                </ListItemSecondaryAction>
  
              </ListItem>
            ))
          }
        </List>
  
        <Dialog open={open} onClose={handleClose}>
          <DialogContent>
            <TextField
              autoFocus
              margin="normal"
              label="Update Todo"
              type="text"
              fullWidth
              name="updateTodo"
              value={update}
              onChange={event => setUpdate(event.target.value)}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} color="primary">
              Cancel
            </Button>
            <Button onClick={editTodo} color="primary">
              Save
            </Button>
          </DialogActions>
        </Dialog>
  
  
      </Container >
    );
  }

  export default Regex;
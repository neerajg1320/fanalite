import React, { useState, useEffect } from 'react';

import firebase from 'firebase';
import realtimeDatabase from '../../firebaseConfig'

import { AddCircleOutlineRounded, DeleteOutlineRounded, Edit } from '@material-ui/icons';
import { Button, TextField, Container, IconButton, List, ListItem, ListItemSecondaryAction, ListItemText, Dialog, DialogContent, DialogActions } from '@material-ui/core';

import {format} from 'date-fns';


function Regex() {
    const resource = 'regexModels';

    const [regexList, setRegexList] = useState([]);
    const [input, setInput] = useState('');
    const [open, setOpen] = useState(false);
    const [update, setUpdate] = useState('');
    const [currentId, setCurrentId] = useState('');
  
  
    useEffect(() => {
      console.log('useEffect Hook!!!');
  
      // .once can be used for onetime only
      realtimeDatabase.ref(resource).on('value', (snapshot) => {
        const regexListSnaphot = snapshot.val();
        const regexList = [];
        for (let id in regexListSnaphot) {
          const title = regexListSnaphot[id].title;
          const regex = regexListSnaphot[id].regex;
          const datetimeEpoch = regexListSnaphot[id].datetime;
          const datetime = datetimeEpoch ? format(new Date(datetimeEpoch), 'yyyy/MM/dd HH:mm:ss SSS') : 'NA';

          regexList.push({
            id, 
            title,
            regex, 
            datetime
          });
        }
        
        console.log('regexList:', regexList);
        
        setRegexList(regexList);
      })
  
    }, []);
  
    // On addition we get two value event with different timestamps
    const addRegex = (event) => {
      event.preventDefault();
  
      realtimeDatabase.ref(resource).push({
        title: input,
        datetime: firebase.database.ServerValue.TIMESTAMP
      })
  
      // Check if we can declare successful addition.
      setInput('');
    }
  
    const deleteRegex = (id) => {
      realtimeDatabase.ref(resource).child(id).remove();
    }
  
    const openUpdateDialog = (regex) => {
      setOpen(true);
      setCurrentId(regex.id);
      setUpdate(regex.title);
    }
  
    const editRegex = () => {
      realtimeDatabase.ref(resource).child(currentId).set({
        title: update,
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
            id="regex"
            label="Enter Regualar Expression"
            name="regex"
            autoFocus
            value={input}
            onChange={event => setInput(event.target.value)}
          />
  
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            onClick={addRegex}
            disabled={!input}
            startIcon={<AddCircleOutlineRounded />}
          >
            Add Regex
        </Button>
  
        </form>
  
        <List dense={true}>
          {
            regexList.map(regexItem => (
  
              <ListItem key={regexItem.id} >
  
                <ListItemText
                  primary={regexItem.title}
                  secondary={regexItem.regex}
                />
  
                <ListItemSecondaryAction>
                  <IconButton edge="end" aria-label="Edit" onClick={() => openUpdateDialog(regexItem)}>
                    <Edit />
                  </IconButton>
                  <IconButton edge="end" aria-label="delete" onClick={() => deleteRegex(regexItem.id)}>
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
              label="Update Regex"
              type="text"
              fullWidth
              name="updateRegex"
              value={update}
              onChange={event => setUpdate(event.target.value)}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} color="primary">
              Cancel
            </Button>
            <Button onClick={editRegex} color="primary">
              Save
            </Button>
          </DialogActions>
        </Dialog>
  
  
      </Container >
    );
  }

  export default Regex;
import React, { useState, useEffect } from 'react';

import firebase from 'firebase';
import { firebaseDatabase as realtimeDatabase } from '../../firebaseConfig'

import { AddCircleOutlineRounded, DeleteOutlineRounded, Edit } from '@material-ui/icons';
import { Button, TextField, Container, IconButton, List, ListItem, ListItemSecondaryAction, ListItemText, Dialog, DialogContent, DialogActions } from '@material-ui/core';

import {format} from 'date-fns';
import axios from 'axios';

import config from '../config/default';
import {useAuth} from "../authentication/AuthContext";

function Regex() {
    const resource = 'regexModels';

    const [regexList, setRegexList] = useState([]);
    const [title, setTitle] = useState('');
    const [regex, setRegex] = useState('');
    const [open, setOpen] = useState(false);
    const [updateTitle, setUpdateTitle] = useState('');
    const [updateRegex, setUpdateRegex] = useState('');
    const [currentId, setCurrentId] = useState('');

    const { authTokens, setAuthTokens } = useAuth();

    // Need to extract this code and put in its own file
    function postNodeServerLogin(user, password) {
        axios.post(config.server.login, {
            strategy: "local",
            email: user,
            password
        }).then(result => {
            console.log("Login Success:", result.status);
            // console.log("result.data:", result.data);

            if (result.status === 201) {
                setAuthTokens(result.data);
            }
        }).catch(e => {
            console.log("exception:", e.message)
        })
    }

    // When
    useEffect(() => {
        postNodeServerLogin("system@abc.com", "System123");
    }, []);

    useEffect(() => {
        console.log("useEffect[authTokens]: authTokens", authTokens);

        if (authTokens) {
            refreshList();
        }
    }, [authTokens]);

    const refreshList = () => {
        getRegexList();
    };

    const getRegexList = () => {
        if (config.backend.selected === "firebase") {
            // .once can be used for onetime only
            realtimeDatabase.ref(resource).on('value', (snapshot) => {
                const regexListSnaphot = snapshot.val();
                const regexList = [];
                for (let id in regexListSnaphot) {
                    const title = regexListSnaphot[id].title;
                    const regex = regexListSnaphot[id].regex;
                    const datetimeEpoch = regexListSnaphot[id].datetime;
                    const datetime = datetimeEpoch ? format(new Date(datetimeEpoch), 'yyyy/MM/dd HH:mm:ss SSS') : 'NA';

                    regexList.push({id, title, regex, datetime});
                }

                setRegexList(regexList);
            })
        } if (config.backend.selected === "fanalite-server") {
            const getRegexListServer = async () => {
                // console.log("getRegexList: authTokens:", authTokens);

                const regexList = [];
                const response = await axios.get(config.server.resources.rules,
                    {
                        headers: {
                            'Authorization': `Bearer ${authTokens.accessToken}`
                        }
                    });

                console.log(response.data);
                const list = response.data.data;
                for (let item in list) {
                    console.log("item:", list[item]);
                    const title = list[item].title;
                    const regex = list[item].regex;
                    const id = list[item].id;
                    const datetime = 'NA';

                    regexList.push({id, title, regex, datetime});
                }
                setRegexList(regexList);
            };

            if (authTokens) {
                getRegexListServer();
            }
        }
    }
  
    // On addition we get two value event with different timestamps
    const addRegex = (event) => {
      event.preventDefault();

        if (config.backend.selected === "firebase") {
            const id = realtimeDatabase.ref(resource).push().key;

            realtimeDatabase.ref(resource).child(id).set({
                id,
                title,
                regex,
                datetime: firebase.database.ServerValue.TIMESTAMP
            });
        } else if (config.backend.selected === "fanalite-server") {
            const addRegexServer = async () => {
                const newRegex = {title, regex};
                const response = await axios.post(config.server.resources.rules,
                    newRegex,
                    {
                        headers: {
                            'Authorization': `Bearer ${authTokens.accessToken}`
                        }
                    });

                console.log("response.data:", response.data);
                refreshList();
            };

            addRegexServer();
        }
  
      // Check if we can declare successful addition.
      setTitle('');
      setRegex('');
    };
  
    const deleteRegex = (id) => {
        if (config.backend.selected === "firebase") {
            realtimeDatabase.ref(resource).child(id).remove();
        } else if (config.backend.selected === "fanalite-server") {
            console.log("Delete Regex: ", id);
            const deleteRegexServer = async () => {
                const response = await axios.delete(config.server.resources.rules + "/" + id,
                    {
                        headers: {
                            'Authorization': `Bearer ${authTokens.accessToken}`
                        }
                    });

                console.log("response.data:", response.data);
                refreshList();
            };

            deleteRegexServer();
        }
    };
  

    const editRegex = () => {
        if (config.backend.selected === "firebase") {
            realtimeDatabase.ref(resource).child(currentId).set({
                id: currentId,
                title: updateTitle,
                regex: updateRegex,
                datetime: firebase.database.ServerValue.TIMESTAMP
            });
        } else if (config.backend.selected === "fanalite-server") {
            console.log("Update Regex: ", currentId);
            const updateRegexServer = async () => {
                const response = await axios.put(config.server.resources.rules + "/" + currentId,
                    {
                        title: updateTitle,
                        regex: updateRegex
                    },
                    {
                        headers: {
                            'Authorization': `Bearer ${authTokens.accessToken}`
                        }
                    });

                console.log("response.data:", response.data);
                refreshList();
            };

            updateRegexServer();
        }

        setOpen(false);
    };


    const openUpdateDialog = (regexItem) => {
        setOpen(true);
        setCurrentId(regexItem.id);
        setUpdateTitle(regexItem.title);
        setUpdateRegex(regexItem.regex);
    };

    const handleClose = () => {
      setOpen(false);
    };
  
    return (
      <Container maxWidth="sm">
  
        <form noValidate>
          <div>
            <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="regex"
                label="Title"
                name="regex"
                autoFocus
                value={title}
                onChange={event => setTitle(event.target.value)}
            />
    
            <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="regex"
                label="Regular Expression"
                name="regex"
                autoFocus
                value={regex}
                onChange={event => setRegex(event.target.value)}
            />
          </div>
          <div style={{marginTop: "20px"}}></div>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            onClick={addRegex}
            disabled={!title}
            startIcon={<AddCircleOutlineRounded />}
          >
            Add Regex
        </Button>
        <div style={{marginTop: "20px"}}></div>
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
              label="Update Title"
              type="text"
              fullWidth
              name="updateTitle"
              value={updateTitle}
              onChange={event => setUpdateTitle(event.target.value)}
            />
            <TextField
              autoFocus
              margin="normal"
              label="Update Regex"
              type="text"
              fullWidth
              name="updateRegex"
              value={updateRegex}
              onChange={event => setUpdateRegex(event.target.value)}
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
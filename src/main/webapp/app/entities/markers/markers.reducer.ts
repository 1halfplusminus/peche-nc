import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMarkers, defaultValue } from 'app/shared/model/markers.model';

export const ACTION_TYPES = {
  SEARCH_MARKERS: 'markers/SEARCH_MARKERS',
  FETCH_MARKERS_LIST: 'markers/FETCH_MARKERS_LIST',
  FETCH_MARKERS: 'markers/FETCH_MARKERS',
  CREATE_MARKERS: 'markers/CREATE_MARKERS',
  UPDATE_MARKERS: 'markers/UPDATE_MARKERS',
  DELETE_MARKERS: 'markers/DELETE_MARKERS',
  SET_BLOB: 'markers/SET_BLOB',
  RESET: 'markers/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMarkers>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MarkersState = Readonly<typeof initialState>;

// Reducer

export default (state: MarkersState = initialState, action): MarkersState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MARKERS):
    case REQUEST(ACTION_TYPES.FETCH_MARKERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MARKERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MARKERS):
    case REQUEST(ACTION_TYPES.UPDATE_MARKERS):
    case REQUEST(ACTION_TYPES.DELETE_MARKERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_MARKERS):
    case FAILURE(ACTION_TYPES.FETCH_MARKERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MARKERS):
    case FAILURE(ACTION_TYPES.CREATE_MARKERS):
    case FAILURE(ACTION_TYPES.UPDATE_MARKERS):
    case FAILURE(ACTION_TYPES.DELETE_MARKERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MARKERS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MARKERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MARKERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MARKERS):
    case SUCCESS(ACTION_TYPES.UPDATE_MARKERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MARKERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/markers';
const apiSearchUrl = 'api/_search/markers';

// Actions

export const getSearchEntities: ICrudSearchAction<IMarkers> = query => ({
  type: ACTION_TYPES.SEARCH_MARKERS,
  payload: axios.get<IMarkers>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IMarkers> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MARKERS_LIST,
  payload: axios.get<IMarkers>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMarkers> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MARKERS,
    payload: axios.get<IMarkers>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMarkers> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MARKERS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMarkers> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MARKERS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMarkers> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MARKERS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

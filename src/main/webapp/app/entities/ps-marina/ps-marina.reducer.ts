import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPsMarina, defaultValue } from 'app/shared/model/ps-marina.model';

export const ACTION_TYPES = {
  SEARCH_PSMARINAS: 'psMarina/SEARCH_PSMARINAS',
  FETCH_PSMARINA_LIST: 'psMarina/FETCH_PSMARINA_LIST',
  FETCH_PSMARINA: 'psMarina/FETCH_PSMARINA',
  CREATE_PSMARINA: 'psMarina/CREATE_PSMARINA',
  UPDATE_PSMARINA: 'psMarina/UPDATE_PSMARINA',
  DELETE_PSMARINA: 'psMarina/DELETE_PSMARINA',
  SET_BLOB: 'psMarina/SET_BLOB',
  RESET: 'psMarina/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPsMarina>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PsMarinaState = Readonly<typeof initialState>;

// Reducer

export default (state: PsMarinaState = initialState, action): PsMarinaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PSMARINAS):
    case REQUEST(ACTION_TYPES.FETCH_PSMARINA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PSMARINA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PSMARINA):
    case REQUEST(ACTION_TYPES.UPDATE_PSMARINA):
    case REQUEST(ACTION_TYPES.DELETE_PSMARINA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PSMARINAS):
    case FAILURE(ACTION_TYPES.FETCH_PSMARINA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PSMARINA):
    case FAILURE(ACTION_TYPES.CREATE_PSMARINA):
    case FAILURE(ACTION_TYPES.UPDATE_PSMARINA):
    case FAILURE(ACTION_TYPES.DELETE_PSMARINA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PSMARINAS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PSMARINA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PSMARINA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PSMARINA):
    case SUCCESS(ACTION_TYPES.UPDATE_PSMARINA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PSMARINA):
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

const apiUrl = 'api/ps-marinas';
const apiSearchUrl = 'api/_search/ps-marinas';

// Actions

export const getSearchEntities: ICrudSearchAction<IPsMarina> = query => ({
  type: ACTION_TYPES.SEARCH_PSMARINAS,
  payload: axios.get<IPsMarina>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IPsMarina> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PSMARINA_LIST,
  payload: axios.get<IPsMarina>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPsMarina> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PSMARINA,
    payload: axios.get<IPsMarina>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPsMarina> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PSMARINA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPsMarina> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PSMARINA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPsMarina> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PSMARINA,
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

import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPsMiseAEau, defaultValue } from 'app/shared/model/ps-mise-a-eau.model';

export const ACTION_TYPES = {
  SEARCH_PSMISEAEAUS: 'psMiseAEau/SEARCH_PSMISEAEAUS',
  FETCH_PSMISEAEAU_LIST: 'psMiseAEau/FETCH_PSMISEAEAU_LIST',
  FETCH_PSMISEAEAU: 'psMiseAEau/FETCH_PSMISEAEAU',
  CREATE_PSMISEAEAU: 'psMiseAEau/CREATE_PSMISEAEAU',
  UPDATE_PSMISEAEAU: 'psMiseAEau/UPDATE_PSMISEAEAU',
  DELETE_PSMISEAEAU: 'psMiseAEau/DELETE_PSMISEAEAU',
  SET_BLOB: 'psMiseAEau/SET_BLOB',
  RESET: 'psMiseAEau/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPsMiseAEau>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PsMiseAEauState = Readonly<typeof initialState>;

// Reducer

export default (state: PsMiseAEauState = initialState, action): PsMiseAEauState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PSMISEAEAUS):
    case REQUEST(ACTION_TYPES.FETCH_PSMISEAEAU_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PSMISEAEAU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PSMISEAEAU):
    case REQUEST(ACTION_TYPES.UPDATE_PSMISEAEAU):
    case REQUEST(ACTION_TYPES.DELETE_PSMISEAEAU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PSMISEAEAUS):
    case FAILURE(ACTION_TYPES.FETCH_PSMISEAEAU_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PSMISEAEAU):
    case FAILURE(ACTION_TYPES.CREATE_PSMISEAEAU):
    case FAILURE(ACTION_TYPES.UPDATE_PSMISEAEAU):
    case FAILURE(ACTION_TYPES.DELETE_PSMISEAEAU):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PSMISEAEAUS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PSMISEAEAU_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PSMISEAEAU):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PSMISEAEAU):
    case SUCCESS(ACTION_TYPES.UPDATE_PSMISEAEAU):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PSMISEAEAU):
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

const apiUrl = 'api/ps-mise-a-eaus';
const apiSearchUrl = 'api/_search/ps-mise-a-eaus';

// Actions

export const getSearchEntities: ICrudSearchAction<IPsMiseAEau> = query => ({
  type: ACTION_TYPES.SEARCH_PSMISEAEAUS,
  payload: axios.get<IPsMiseAEau>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IPsMiseAEau> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PSMISEAEAU_LIST,
  payload: axios.get<IPsMiseAEau>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPsMiseAEau> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PSMISEAEAU,
    payload: axios.get<IPsMiseAEau>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPsMiseAEau> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PSMISEAEAU,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPsMiseAEau> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PSMISEAEAU,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPsMiseAEau> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PSMISEAEAU,
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

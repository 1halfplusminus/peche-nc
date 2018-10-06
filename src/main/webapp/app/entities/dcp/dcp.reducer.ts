import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDCP, defaultValue } from 'app/shared/model/dcp.model';

export const ACTION_TYPES = {
  SEARCH_DCPS: 'dCP/SEARCH_DCPS',
  FETCH_DCP_LIST: 'dCP/FETCH_DCP_LIST',
  FETCH_DCP: 'dCP/FETCH_DCP',
  CREATE_DCP: 'dCP/CREATE_DCP',
  UPDATE_DCP: 'dCP/UPDATE_DCP',
  DELETE_DCP: 'dCP/DELETE_DCP',
  RESET: 'dCP/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDCP>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DCPState = Readonly<typeof initialState>;

// Reducer

export default (state: DCPState = initialState, action): DCPState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_DCPS):
    case REQUEST(ACTION_TYPES.FETCH_DCP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DCP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DCP):
    case REQUEST(ACTION_TYPES.UPDATE_DCP):
    case REQUEST(ACTION_TYPES.DELETE_DCP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_DCPS):
    case FAILURE(ACTION_TYPES.FETCH_DCP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DCP):
    case FAILURE(ACTION_TYPES.CREATE_DCP):
    case FAILURE(ACTION_TYPES.UPDATE_DCP):
    case FAILURE(ACTION_TYPES.DELETE_DCP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_DCPS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DCP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DCP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DCP):
    case SUCCESS(ACTION_TYPES.UPDATE_DCP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DCP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/dcps';
const apiSearchUrl = 'api/_search/dcps';

// Actions

export const getSearchEntities: ICrudSearchAction<IDCP> = query => ({
  type: ACTION_TYPES.SEARCH_DCPS,
  payload: axios.get<IDCP>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IDCP> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DCP_LIST,
  payload: axios.get<IDCP>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDCP> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DCP,
    payload: axios.get<IDCP>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDCP> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DCP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDCP> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DCP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDCP> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DCP,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

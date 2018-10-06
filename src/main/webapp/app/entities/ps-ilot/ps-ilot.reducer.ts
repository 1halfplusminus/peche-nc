import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPsIlot, defaultValue } from 'app/shared/model/ps-ilot.model';

export const ACTION_TYPES = {
  SEARCH_PSILOTS: 'psIlot/SEARCH_PSILOTS',
  FETCH_PSILOT_LIST: 'psIlot/FETCH_PSILOT_LIST',
  FETCH_PSILOT: 'psIlot/FETCH_PSILOT',
  CREATE_PSILOT: 'psIlot/CREATE_PSILOT',
  UPDATE_PSILOT: 'psIlot/UPDATE_PSILOT',
  DELETE_PSILOT: 'psIlot/DELETE_PSILOT',
  SET_BLOB: 'psIlot/SET_BLOB',
  RESET: 'psIlot/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPsIlot>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PsIlotState = Readonly<typeof initialState>;

// Reducer

export default (state: PsIlotState = initialState, action): PsIlotState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PSILOTS):
    case REQUEST(ACTION_TYPES.FETCH_PSILOT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PSILOT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PSILOT):
    case REQUEST(ACTION_TYPES.UPDATE_PSILOT):
    case REQUEST(ACTION_TYPES.DELETE_PSILOT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PSILOTS):
    case FAILURE(ACTION_TYPES.FETCH_PSILOT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PSILOT):
    case FAILURE(ACTION_TYPES.CREATE_PSILOT):
    case FAILURE(ACTION_TYPES.UPDATE_PSILOT):
    case FAILURE(ACTION_TYPES.DELETE_PSILOT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PSILOTS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PSILOT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PSILOT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PSILOT):
    case SUCCESS(ACTION_TYPES.UPDATE_PSILOT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PSILOT):
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

const apiUrl = 'api/ps-ilots';
const apiSearchUrl = 'api/_search/ps-ilots';

// Actions

export const getSearchEntities: ICrudSearchAction<IPsIlot> = query => ({
  type: ACTION_TYPES.SEARCH_PSILOTS,
  payload: axios.get<IPsIlot>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IPsIlot> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PSILOT_LIST,
  payload: axios.get<IPsIlot>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPsIlot> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PSILOT,
    payload: axios.get<IPsIlot>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPsIlot> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PSILOT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPsIlot> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PSILOT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPsIlot> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PSILOT,
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

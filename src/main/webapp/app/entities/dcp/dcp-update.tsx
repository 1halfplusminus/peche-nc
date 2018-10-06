import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './dcp.reducer';
import { IDCP } from 'app/shared/model/dcp.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDCPUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDCPUpdateState {
  isNew: boolean;
}

export class DCPUpdate extends React.Component<IDCPUpdateProps, IDCPUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { dCPEntity } = this.props;
      const entity = {
        ...dCPEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/dcp');
  };

  render() {
    const { dCPEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pechencApp.dCP.home.createOrEditLabel">
              <Translate contentKey="pechencApp.dCP.home.createOrEditLabel">Create or edit a DCP</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dCPEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="dcp-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomLabel" for="nom">
                    <Translate contentKey="pechencApp.dCP.nom">Nom</Translate>
                  </Label>
                  <AvField
                    id="dcp-nom"
                    type="text"
                    name="nom"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="positionLabel" for="position">
                    <Translate contentKey="pechencApp.dCP.position">Position</Translate>
                  </Label>
                  <AvField id="dcp-position" type="text" name="position" />
                </AvGroup>
                <AvGroup>
                  <Label id="latLabel" for="lat">
                    <Translate contentKey="pechencApp.dCP.lat">Lat</Translate>
                  </Label>
                  <AvField id="dcp-lat" type="string" className="form-control" name="lat" />
                </AvGroup>
                <AvGroup>
                  <Label id="lngLabel" for="lng">
                    <Translate contentKey="pechencApp.dCP.lng">Lng</Translate>
                  </Label>
                  <AvField id="dcp-lng" type="string" className="form-control" name="lng" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateDerniereMajLabel" for="dateDerniereMaj">
                    <Translate contentKey="pechencApp.dCP.dateDerniereMaj">Date Derniere Maj</Translate>
                  </Label>
                  <AvField id="dcp-dateDerniereMaj" type="date" className="form-control" name="dateDerniereMaj" />
                </AvGroup>
                <AvGroup>
                  <Label id="etatLabel">
                    <Translate contentKey="pechencApp.dCP.etat">Etat</Translate>
                  </Label>
                  <AvInput id="dcp-etat" type="select" className="form-control" name="etat" value={(!isNew && dCPEntity.etat) || 'DISPARU'}>
                    <option value="DISPARU">
                      <Translate contentKey="pechencApp.DCPEtat.DISPARU" />
                    </option>
                    <option value="NORMAL">
                      <Translate contentKey="pechencApp.DCPEtat.NORMAL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="localisationLabel" for="localisation">
                    <Translate contentKey="pechencApp.dCP.localisation">Localisation</Translate>
                  </Label>
                  <AvField id="dcp-localisation" type="text" name="localisation" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/dcp" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  dCPEntity: storeState.dCP.entity,
  loading: storeState.dCP.loading,
  updating: storeState.dCP.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DCPUpdate);

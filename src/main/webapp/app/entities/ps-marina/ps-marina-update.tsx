import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './ps-marina.reducer';
import { IPsMarina } from 'app/shared/model/ps-marina.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPsMarinaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPsMarinaUpdateState {
  isNew: boolean;
}

export class PsMarinaUpdate extends React.Component<IPsMarinaUpdateProps, IPsMarinaUpdateState> {
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

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { psMarinaEntity } = this.props;
      const entity = {
        ...psMarinaEntity,
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
    this.props.history.push('/entity/ps-marina');
  };

  render() {
    const { psMarinaEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { photos, photosContentType } = psMarinaEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pechencApp.psMarina.home.createOrEditLabel">
              <Translate contentKey="pechencApp.psMarina.home.createOrEditLabel">Create or edit a PsMarina</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : psMarinaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ps-marina-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="latLabel" for="lat">
                    <Translate contentKey="pechencApp.psMarina.lat">Lat</Translate>
                  </Label>
                  <AvField id="ps-marina-lat" type="string" className="form-control" name="lat" />
                </AvGroup>
                <AvGroup>
                  <Label id="lngLabel" for="lng">
                    <Translate contentKey="pechencApp.psMarina.lng">Lng</Translate>
                  </Label>
                  <AvField id="ps-marina-lng" type="string" className="form-control" name="lng" />
                </AvGroup>
                <AvGroup>
                  <Label id="idMarinaLabel" for="idMarina">
                    <Translate contentKey="pechencApp.psMarina.idMarina">Id Marina</Translate>
                  </Label>
                  <AvField id="ps-marina-idMarina" type="text" name="idMarina" />
                </AvGroup>
                <AvGroup>
                  <Label id="accessLabel" for="access">
                    <Translate contentKey="pechencApp.psMarina.access">Access</Translate>
                  </Label>
                  <AvField id="ps-marina-access" type="text" name="access" />
                </AvGroup>
                <AvGroup>
                  <Label id="amenageeLabel" check>
                    <AvInput id="ps-marina-amenagee" type="checkbox" className="form-control" name="amenagee" />
                    <Translate contentKey="pechencApp.psMarina.amenagee">Amenagee</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="communeLabel" for="commune">
                    <Translate contentKey="pechencApp.psMarina.commune">Commune</Translate>
                  </Label>
                  <AvField id="ps-marina-commune" type="text" name="commune" />
                </AvGroup>
                <AvGroup>
                  <Label id="lieuDitLabel" for="lieuDit">
                    <Translate contentKey="pechencApp.psMarina.lieuDit">Lieu Dit</Translate>
                  </Label>
                  <AvField id="ps-marina-lieuDit" type="text" name="lieuDit" />
                </AvGroup>
                <AvGroup>
                  <Label id="parkingPlaceLabel" for="parkingPlace">
                    <Translate contentKey="pechencApp.psMarina.parkingPlace">Parking Place</Translate>
                  </Label>
                  <AvField id="ps-marina-parkingPlace" type="string" className="form-control" name="parkingPlace" />
                </AvGroup>
                <AvGroup>
                  <Label id="observationLabel" for="observation">
                    <Translate contentKey="pechencApp.psMarina.observation">Observation</Translate>
                  </Label>
                  <AvField id="ps-marina-observation" type="text" name="observation" />
                </AvGroup>
                <AvGroup>
                  <Label id="parkingLabel" check>
                    <AvInput id="ps-marina-parking" type="checkbox" className="form-control" name="parking" />
                    <Translate contentKey="pechencApp.psMarina.parking">Parking</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="payantLabel" check>
                    <AvInput id="ps-marina-payant" type="checkbox" className="form-control" name="payant" />
                    <Translate contentKey="pechencApp.psMarina.payant">Payant</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="pointEauLabel" check>
                    <AvInput id="ps-marina-pointEau" type="checkbox" className="form-control" name="pointEau" />
                    <Translate contentKey="pechencApp.psMarina.pointEau">Point Eau</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="poubellesLabel" check>
                    <AvInput id="ps-marina-poubelles" type="checkbox" className="form-control" name="poubelles" />
                    <Translate contentKey="pechencApp.psMarina.poubelles">Poubelles</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="toilettesLabel" check>
                    <AvInput id="ps-marina-toilettes" type="checkbox" className="form-control" name="toilettes" />
                    <Translate contentKey="pechencApp.psMarina.toilettes">Toilettes</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="titreLabel" for="titre">
                    <Translate contentKey="pechencApp.psMarina.titre">Titre</Translate>
                  </Label>
                  <AvField id="ps-marina-titre" type="text" name="titre" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="photosLabel" for="photos">
                      <Translate contentKey="pechencApp.psMarina.photos">Photos</Translate>
                    </Label>
                    <br />
                    {photos ? (
                      <div>
                        <a onClick={openFile(photosContentType, photos)}>
                          <img src={`data:${photosContentType};base64,${photos}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {photosContentType}, {byteSize(photos)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('photos')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_photos" type="file" onChange={this.onBlobChange(true, 'photos')} accept="image/*" />
                    <AvInput type="hidden" name="photos" value={photos} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="pechencApp.psMarina.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="ps-marina-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && psMarinaEntity.status) || 'VALIDE'}
                  >
                    <option value="VALIDE">
                      <Translate contentKey="pechencApp.PSStatus.VALIDE" />
                    </option>
                    <option value="NONVALIDE">
                      <Translate contentKey="pechencApp.PSStatus.NONVALIDE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ps-marina" replace color="info">
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
  psMarinaEntity: storeState.psMarina.entity,
  loading: storeState.psMarina.loading,
  updating: storeState.psMarina.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PsMarinaUpdate);

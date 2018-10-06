import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPsMarina } from 'app/shared/model/ps-marina.model';
import { getEntities as getPsMarinas } from 'app/entities/ps-marina/ps-marina.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './ps-mise-a-eau.reducer';
import { IPsMiseAEau } from 'app/shared/model/ps-mise-a-eau.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPsMiseAEauUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPsMiseAEauUpdateState {
  isNew: boolean;
  marinasId: string;
}

export class PsMiseAEauUpdate extends React.Component<IPsMiseAEauUpdateProps, IPsMiseAEauUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      marinasId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getPsMarinas();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { psMiseAEauEntity } = this.props;
      const entity = {
        ...psMiseAEauEntity,
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
    this.props.history.push('/entity/ps-mise-a-eau');
  };

  render() {
    const { psMiseAEauEntity, psMarinas, loading, updating } = this.props;
    const { isNew } = this.state;

    const { photos, photosContentType } = psMiseAEauEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pechencApp.psMiseAEau.home.createOrEditLabel">
              <Translate contentKey="pechencApp.psMiseAEau.home.createOrEditLabel">Create or edit a PsMiseAEau</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : psMiseAEauEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ps-mise-a-eau-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="latLabel" for="lat">
                    <Translate contentKey="pechencApp.psMiseAEau.lat">Lat</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-lat" type="string" className="form-control" name="lat" />
                </AvGroup>
                <AvGroup>
                  <Label id="lngLabel" for="lng">
                    <Translate contentKey="pechencApp.psMiseAEau.lng">Lng</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-lng" type="string" className="form-control" name="lng" />
                </AvGroup>
                <AvGroup>
                  <Label id="accessLabel" for="access">
                    <Translate contentKey="pechencApp.psMiseAEau.access">Access</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-access" type="text" name="access" />
                </AvGroup>
                <AvGroup>
                  <Label id="amenageeLabel" check>
                    <AvInput id="ps-mise-a-eau-amenagee" type="checkbox" className="form-control" name="amenagee" />
                    <Translate contentKey="pechencApp.psMiseAEau.amenagee">Amenagee</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="communeLabel" for="commune">
                    <Translate contentKey="pechencApp.psMiseAEau.commune">Commune</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-commune" type="text" name="commune" />
                </AvGroup>
                <AvGroup>
                  <Label id="lieuDitLabel" for="lieuDit">
                    <Translate contentKey="pechencApp.psMiseAEau.lieuDit">Lieu Dit</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-lieuDit" type="text" name="lieuDit" />
                </AvGroup>
                <AvGroup>
                  <Label id="parkingPlaceLabel" for="parkingPlace">
                    <Translate contentKey="pechencApp.psMiseAEau.parkingPlace">Parking Place</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-parkingPlace" type="string" className="form-control" name="parkingPlace" />
                </AvGroup>
                <AvGroup>
                  <Label id="observationLabel" for="observation">
                    <Translate contentKey="pechencApp.psMiseAEau.observation">Observation</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-observation" type="text" name="observation" />
                </AvGroup>
                <AvGroup>
                  <Label id="parkingLabel" check>
                    <AvInput id="ps-mise-a-eau-parking" type="checkbox" className="form-control" name="parking" />
                    <Translate contentKey="pechencApp.psMiseAEau.parking">Parking</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="payantLabel" check>
                    <AvInput id="ps-mise-a-eau-payant" type="checkbox" className="form-control" name="payant" />
                    <Translate contentKey="pechencApp.psMiseAEau.payant">Payant</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="pointEauLabel" check>
                    <AvInput id="ps-mise-a-eau-pointEau" type="checkbox" className="form-control" name="pointEau" />
                    <Translate contentKey="pechencApp.psMiseAEau.pointEau">Point Eau</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="poubellesLabel" check>
                    <AvInput id="ps-mise-a-eau-poubelles" type="checkbox" className="form-control" name="poubelles" />
                    <Translate contentKey="pechencApp.psMiseAEau.poubelles">Poubelles</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="toilettesLabel" check>
                    <AvInput id="ps-mise-a-eau-toilettes" type="checkbox" className="form-control" name="toilettes" />
                    <Translate contentKey="pechencApp.psMiseAEau.toilettes">Toilettes</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="titreLabel" for="titre">
                    <Translate contentKey="pechencApp.psMiseAEau.titre">Titre</Translate>
                  </Label>
                  <AvField id="ps-mise-a-eau-titre" type="text" name="titre" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="photosLabel" for="photos">
                      <Translate contentKey="pechencApp.psMiseAEau.photos">Photos</Translate>
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
                    <Translate contentKey="pechencApp.psMiseAEau.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="ps-mise-a-eau-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && psMiseAEauEntity.status) || 'VALIDE'}
                  >
                    <option value="VALIDE">
                      <Translate contentKey="pechencApp.PSStatus.VALIDE" />
                    </option>
                    <option value="NONVALIDE">
                      <Translate contentKey="pechencApp.PSStatus.NONVALIDE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="marinas.idMarina">
                    <Translate contentKey="pechencApp.psMiseAEau.marinas">Marinas</Translate>
                  </Label>
                  <AvInput id="ps-mise-a-eau-marinas" type="select" className="form-control" name="marinas.id">
                    <option value="" key="0" />
                    {psMarinas
                      ? psMarinas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.idMarina}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ps-mise-a-eau" replace color="info">
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
  psMarinas: storeState.psMarina.entities,
  psMiseAEauEntity: storeState.psMiseAEau.entity,
  loading: storeState.psMiseAEau.loading,
  updating: storeState.psMiseAEau.updating
});

const mapDispatchToProps = {
  getPsMarinas,
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
)(PsMiseAEauUpdate);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './ps-ilot.reducer';
import { IPsIlot } from 'app/shared/model/ps-ilot.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPsIlotUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPsIlotUpdateState {
  isNew: boolean;
}

export class PsIlotUpdate extends React.Component<IPsIlotUpdateProps, IPsIlotUpdateState> {
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
      const { psIlotEntity } = this.props;
      const entity = {
        ...psIlotEntity,
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
    this.props.history.push('/entity/ps-ilot');
  };

  render() {
    const { psIlotEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { photo, photoContentType } = psIlotEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pechencApp.psIlot.home.createOrEditLabel">
              <Translate contentKey="pechencApp.psIlot.home.createOrEditLabel">Create or edit a PsIlot</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : psIlotEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ps-ilot-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="latLabel" for="lat">
                    <Translate contentKey="pechencApp.psIlot.lat">Lat</Translate>
                  </Label>
                  <AvField id="ps-ilot-lat" type="string" className="form-control" name="lat" />
                </AvGroup>
                <AvGroup>
                  <Label id="lngLabel" for="lng">
                    <Translate contentKey="pechencApp.psIlot.lng">Lng</Translate>
                  </Label>
                  <AvField id="ps-ilot-lng" type="string" className="form-control" name="lng" />
                </AvGroup>
                <AvGroup>
                  <Label id="idIlotLabel" for="idIlot">
                    <Translate contentKey="pechencApp.psIlot.idIlot">Id Ilot</Translate>
                  </Label>
                  <AvField id="ps-ilot-idIlot" type="text" name="idIlot" />
                </AvGroup>
                <AvGroup>
                  <Label id="calendrierLabel" for="calendrier">
                    <Translate contentKey="pechencApp.psIlot.calendrier">Calendrier</Translate>
                  </Label>
                  <AvField id="ps-ilot-calendrier" type="text" name="calendrier" />
                </AvGroup>
                <AvGroup>
                  <Label id="communeLabel" for="commune">
                    <Translate contentKey="pechencApp.psIlot.commune">Commune</Translate>
                  </Label>
                  <AvField id="ps-ilot-commune" type="text" name="commune" />
                </AvGroup>
                <AvGroup>
                  <Label id="posehelicoLabel" for="posehelico">
                    <Translate contentKey="pechencApp.psIlot.posehelico">Posehelico</Translate>
                  </Label>
                  <AvField id="ps-ilot-posehelico" type="text" name="posehelico" />
                </AvGroup>
                <AvGroup>
                  <Label id="titreLabel" for="titre">
                    <Translate contentKey="pechencApp.psIlot.titre">Titre</Translate>
                  </Label>
                  <AvField id="ps-ilot-titre" type="text" name="titre" />
                </AvGroup>
                <AvGroup>
                  <Label id="copyrightLabel" for="copyright">
                    <Translate contentKey="pechencApp.psIlot.copyright">Copyright</Translate>
                  </Label>
                  <AvField id="ps-ilot-copyright" type="text" name="copyright" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="photoLabel" for="photo">
                      <Translate contentKey="pechencApp.psIlot.photo">Photo</Translate>
                    </Label>
                    <br />
                    {photo ? (
                      <div>
                        <a onClick={openFile(photoContentType, photo)}>
                          <img src={`data:${photoContentType};base64,${photo}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {photoContentType}, {byteSize(photo)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('photo')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_photo" type="file" onChange={this.onBlobChange(true, 'photo')} accept="image/*" />
                    <AvInput type="hidden" name="photo" value={photo} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="pechencApp.psIlot.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="ps-ilot-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && psIlotEntity.status) || 'VALIDE'}
                  >
                    <option value="VALIDE">
                      <Translate contentKey="pechencApp.PSStatus.VALIDE" />
                    </option>
                    <option value="NONVALIDE">
                      <Translate contentKey="pechencApp.PSStatus.NONVALIDE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ps-ilot" replace color="info">
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
  psIlotEntity: storeState.psIlot.entity,
  loading: storeState.psIlot.loading,
  updating: storeState.psIlot.updating
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
)(PsIlotUpdate);

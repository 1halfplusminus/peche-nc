import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './markers.reducer';
import { IMarkers } from 'app/shared/model/markers.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMarkersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMarkersUpdateState {
  isNew: boolean;
}

export class MarkersUpdate extends React.Component<IMarkersUpdateProps, IMarkersUpdateState> {
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
      const { markersEntity } = this.props;
      const entity = {
        ...markersEntity,
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
    this.props.history.push('/entity/markers');
  };

  render() {
    const { markersEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { icon, iconContentType, description, pj, pjContentType } = markersEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pechencApp.markers.home.createOrEditLabel">
              <Translate contentKey="pechencApp.markers.home.createOrEditLabel">Create or edit a Markers</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : markersEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="markers-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <AvGroup>
                    <Label id="iconLabel" for="icon">
                      <Translate contentKey="pechencApp.markers.icon">Icon</Translate>
                    </Label>
                    <br />
                    {icon ? (
                      <div>
                        <a onClick={openFile(iconContentType, icon)}>
                          <img src={`data:${iconContentType};base64,${icon}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {iconContentType}, {byteSize(icon)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('icon')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_icon" type="file" onChange={this.onBlobChange(true, 'icon')} accept="image/*" />
                    <AvInput type="hidden" name="icon" value={icon} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="titreLabel" for="titre">
                    <Translate contentKey="pechencApp.markers.titre">Titre</Translate>
                  </Label>
                  <AvField id="markers-titre" type="text" name="titre" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="pechencApp.markers.description">Description</Translate>
                  </Label>
                  <AvInput id="markers-description" type="textarea" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    <Translate contentKey="pechencApp.markers.email">Email</Translate>
                  </Label>
                  <AvField id="markers-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="pjLabel" for="pj">
                      <Translate contentKey="pechencApp.markers.pj">Pj</Translate>
                    </Label>
                    <br />
                    {pj ? (
                      <div>
                        <a onClick={openFile(pjContentType, pj)}>
                          <img src={`data:${pjContentType};base64,${pj}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {pjContentType}, {byteSize(pj)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('pj')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_pj" type="file" onChange={this.onBlobChange(true, 'pj')} accept="image/*" />
                    <AvInput type="hidden" name="pj" value={pj} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="ipLabel" for="ip">
                    <Translate contentKey="pechencApp.markers.ip">Ip</Translate>
                  </Label>
                  <AvField id="markers-ip" type="text" name="ip" />
                </AvGroup>
                <AvGroup>
                  <Label id="latLabel" for="lat">
                    <Translate contentKey="pechencApp.markers.lat">Lat</Translate>
                  </Label>
                  <AvField id="markers-lat" type="string" className="form-control" name="lat" />
                </AvGroup>
                <AvGroup>
                  <Label id="lngLabel" for="lng">
                    <Translate contentKey="pechencApp.markers.lng">Lng</Translate>
                  </Label>
                  <AvField id="markers-lng" type="string" className="form-control" name="lng" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="pechencApp.markers.date">Date</Translate>
                  </Label>
                  <AvField id="markers-date" type="date" className="form-control" name="date" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/markers" replace color="info">
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
  markersEntity: storeState.markers.entity,
  loading: storeState.markers.loading,
  updating: storeState.markers.updating
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
)(MarkersUpdate);

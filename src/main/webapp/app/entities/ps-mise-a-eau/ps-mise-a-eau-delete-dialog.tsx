import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPsMiseAEau } from 'app/shared/model/ps-mise-a-eau.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './ps-mise-a-eau.reducer';

export interface IPsMiseAEauDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PsMiseAEauDeleteDialog extends React.Component<IPsMiseAEauDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.psMiseAEauEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { psMiseAEauEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="pechencApp.psMiseAEau.delete.question">
          <Translate contentKey="pechencApp.psMiseAEau.delete.question" interpolate={{ id: psMiseAEauEntity.id }}>
            Are you sure you want to delete this PsMiseAEau?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />&nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-psMiseAEau" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />&nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ psMiseAEau }: IRootState) => ({
  psMiseAEauEntity: psMiseAEau.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PsMiseAEauDeleteDialog);

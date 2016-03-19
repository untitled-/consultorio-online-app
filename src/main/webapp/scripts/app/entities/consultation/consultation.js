'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('consultation', {
                parent: 'entity',
                url: '/consultations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.consultation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/consultation/consultations.html',
                        controller: 'ConsultationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('consultation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('consultation.detail', {
                parent: 'entity',
                url: '/consultation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.consultation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/consultation/consultation-detail.html',
                        controller: 'ConsultationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('consultation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Consultation', function($stateParams, Consultation) {
                        return Consultation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('consultation.new', {
                parent: 'consultation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/consultation/consultation-dialog.html',
                        controller: 'ConsultationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    consultationDate: null,
                                    idx: null,
                                    diferentialDiagnostic: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('consultation', null, { reload: true });
                    }, function() {
                        $state.go('consultation');
                    })
                }]
            })
            .state('consultation.edit', {
                parent: 'consultation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/consultation/consultation-dialog.html',
                        controller: 'ConsultationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Consultation', function(Consultation) {
                                return Consultation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('consultation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('consultation.delete', {
                parent: 'consultation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/consultation/consultation-delete-dialog.html',
                        controller: 'ConsultationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Consultation', function(Consultation) {
                                return Consultation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('consultation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

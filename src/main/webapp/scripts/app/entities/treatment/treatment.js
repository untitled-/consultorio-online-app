'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('treatment', {
                parent: 'entity',
                url: '/treatments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.treatment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/treatment/treatments.html',
                        controller: 'TreatmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('treatment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('treatment.detail', {
                parent: 'entity',
                url: '/treatment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.treatment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/treatment/treatment-detail.html',
                        controller: 'TreatmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('treatment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Treatment', function($stateParams, Treatment) {
                        return Treatment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('treatment.new', {
                parent: 'treatment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/treatment/treatment-dialog.html',
                        controller: 'TreatmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    prescriptionNumber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('treatment', null, { reload: true });
                    }, function() {
                        $state.go('treatment');
                    })
                }]
            })
            .state('treatment.edit', {
                parent: 'treatment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/treatment/treatment-dialog.html',
                        controller: 'TreatmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Treatment', function(Treatment) {
                                return Treatment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('treatment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('treatment.delete', {
                parent: 'treatment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/treatment/treatment-delete-dialog.html',
                        controller: 'TreatmentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Treatment', function(Treatment) {
                                return Treatment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('treatment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('immunization', {
                parent: 'entity',
                url: '/immunizations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.immunization.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/immunization/immunizations.html',
                        controller: 'ImmunizationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('immunization');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('immunization.detail', {
                parent: 'entity',
                url: '/immunization/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.immunization.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/immunization/immunization-detail.html',
                        controller: 'ImmunizationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('immunization');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Immunization', function($stateParams, Immunization) {
                        return Immunization.get({id : $stateParams.id});
                    }]
                }
            })
            .state('immunization.new', {
                parent: 'immunization',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/immunization/immunization-dialog.html',
                        controller: 'ImmunizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('immunization', null, { reload: true });
                    }, function() {
                        $state.go('immunization');
                    })
                }]
            })
            .state('immunization.edit', {
                parent: 'immunization',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/immunization/immunization-dialog.html',
                        controller: 'ImmunizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Immunization', function(Immunization) {
                                return Immunization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('immunization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('immunization.delete', {
                parent: 'immunization',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/immunization/immunization-delete-dialog.html',
                        controller: 'ImmunizationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Immunization', function(Immunization) {
                                return Immunization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('immunization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

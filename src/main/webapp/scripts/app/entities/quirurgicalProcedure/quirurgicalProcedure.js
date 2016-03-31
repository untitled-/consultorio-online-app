'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quirurgicalProcedure', {
                parent: 'entity',
                url: '/quirurgicalProcedures',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.quirurgicalProcedure.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quirurgicalProcedure/quirurgicalProcedures.html',
                        controller: 'QuirurgicalProcedureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quirurgicalProcedure');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('quirurgicalProcedure.detail', {
                parent: 'entity',
                url: '/quirurgicalProcedure/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.quirurgicalProcedure.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quirurgicalProcedure/quirurgicalProcedure-detail.html',
                        controller: 'QuirurgicalProcedureDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quirurgicalProcedure');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'QuirurgicalProcedure', function($stateParams, QuirurgicalProcedure) {
                        return QuirurgicalProcedure.get({id : $stateParams.id});
                    }]
                }
            })
            .state('quirurgicalProcedure.new', {
                parent: 'quirurgicalProcedure',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/quirurgicalProcedure/quirurgicalProcedure-dialog.html',
                        controller: 'QuirurgicalProcedureDialogController',
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
                        $state.go('quirurgicalProcedure', null, { reload: true });
                    }, function() {
                        $state.go('quirurgicalProcedure');
                    })
                }]
            })
            .state('quirurgicalProcedure.edit', {
                parent: 'quirurgicalProcedure',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/quirurgicalProcedure/quirurgicalProcedure-dialog.html',
                        controller: 'QuirurgicalProcedureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['QuirurgicalProcedure', function(QuirurgicalProcedure) {
                                return QuirurgicalProcedure.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quirurgicalProcedure', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('quirurgicalProcedure.delete', {
                parent: 'quirurgicalProcedure',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/quirurgicalProcedure/quirurgicalProcedure-delete-dialog.html',
                        controller: 'QuirurgicalProcedureDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['QuirurgicalProcedure', function(QuirurgicalProcedure) {
                                return QuirurgicalProcedure.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quirurgicalProcedure', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

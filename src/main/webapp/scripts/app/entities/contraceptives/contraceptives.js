'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contraceptives', {
                parent: 'entity',
                url: '/contraceptivess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.contraceptives.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contraceptives/contraceptivess.html',
                        controller: 'ContraceptivesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contraceptives');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contraceptives.detail', {
                parent: 'entity',
                url: '/contraceptives/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.contraceptives.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contraceptives/contraceptives-detail.html',
                        controller: 'ContraceptivesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contraceptives');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Contraceptives', function($stateParams, Contraceptives) {
                        return Contraceptives.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contraceptives.new', {
                parent: 'contraceptives',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contraceptives/contraceptives-dialog.html',
                        controller: 'ContraceptivesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contraceptives', null, { reload: true });
                    }, function() {
                        $state.go('contraceptives');
                    })
                }]
            })
            .state('contraceptives.edit', {
                parent: 'contraceptives',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contraceptives/contraceptives-dialog.html',
                        controller: 'ContraceptivesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contraceptives', function(Contraceptives) {
                                return Contraceptives.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contraceptives', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contraceptives.delete', {
                parent: 'contraceptives',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contraceptives/contraceptives-delete-dialog.html',
                        controller: 'ContraceptivesDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Contraceptives', function(Contraceptives) {
                                return Contraceptives.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contraceptives', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

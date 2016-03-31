'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trauma', {
                parent: 'entity',
                url: '/traumas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.trauma.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trauma/traumas.html',
                        controller: 'TraumaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trauma');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trauma.detail', {
                parent: 'entity',
                url: '/trauma/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.trauma.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trauma/trauma-detail.html',
                        controller: 'TraumaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trauma');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Trauma', function($stateParams, Trauma) {
                        return Trauma.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trauma.new', {
                parent: 'trauma',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trauma/trauma-dialog.html',
                        controller: 'TraumaDialogController',
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
                        $state.go('trauma', null, { reload: true });
                    }, function() {
                        $state.go('trauma');
                    })
                }]
            })
            .state('trauma.edit', {
                parent: 'trauma',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trauma/trauma-dialog.html',
                        controller: 'TraumaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Trauma', function(Trauma) {
                                return Trauma.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trauma', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('trauma.delete', {
                parent: 'trauma',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trauma/trauma-delete-dialog.html',
                        controller: 'TraumaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Trauma', function(Trauma) {
                                return Trauma.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trauma', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('addiction', {
                parent: 'entity',
                url: '/addictions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.addiction.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/addiction/addictions.html',
                        controller: 'AddictionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('addiction');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('addiction.detail', {
                parent: 'entity',
                url: '/addiction/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.addiction.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/addiction/addiction-detail.html',
                        controller: 'AddictionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('addiction');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Addiction', function($stateParams, Addiction) {
                        return Addiction.get({id : $stateParams.id});
                    }]
                }
            })
            .state('addiction.new', {
                parent: 'addiction',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/addiction/addiction-dialog.html',
                        controller: 'AddictionDialogController',
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
                        $state.go('addiction', null, { reload: true });
                    }, function() {
                        $state.go('addiction');
                    })
                }]
            })
            .state('addiction.edit', {
                parent: 'addiction',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/addiction/addiction-dialog.html',
                        controller: 'AddictionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Addiction', function(Addiction) {
                                return Addiction.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('addiction', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('addiction.delete', {
                parent: 'addiction',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/addiction/addiction-delete-dialog.html',
                        controller: 'AddictionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Addiction', function(Addiction) {
                                return Addiction.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('addiction', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
